package com.example.rewear.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rewear.R
import com.example.rewear.data.local.entity.ClothingEntity

// Kolory aplikacji REWEAR
private val Background = Color(0xFF242330)
private val CardColor = Color(0xFF302E3E)
private val CardBorder = Color(0xFF3F3C50)
private val LighterCardColor = Color(0xFF443A5D)
private val Accent = Color(0xFFBCB0D0)
private val TextWhite = Color(0xFFF5F1FA)
private val TextGrey = Color(0xFFB5B0C5)

// Kolory karty kolażu stylizacji
private val CollageBackground = Color(0xFFF3EFEA)
private val CollageEmptySlot = Color(0xFFD5CFC7)
private val CollagePlusTint = Color(0xFF948DA8)

@Composable
fun OutfitCreatorScreen(
    initialName: String = "Na lekko",
    initialItems: List<ClothingEntity> = defaultSampleOutfitItems(),
    onAddClothingClick: () -> Unit = {},
    onSwapClothingClick: (ClothingEntity) -> Unit = {},
    onSaveOutfitClick: (name: String, items: List<ClothingEntity>) -> Unit = { _, _ -> }
) {
    var outfitName by remember { mutableStateOf(initialName) }
    var selectedItems by remember { mutableStateOf(initialItems) }
    var activeDeleteItemId by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                // Dotknięcie dowolnego pustego miejsca na ekranie zamyka przycisk "Usuń"
                if (activeDeleteItemId != null) {
                    activeDeleteItemId = null
                }
            }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            CreatorHeader()

            Spacer(modifier = Modifier.height(20.dp))

            OutfitNameCard(
                value = outfitName,
                onValueChange = { outfitName = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutfitCollageCard(
                items = selectedItems,
                activeDeleteItemId = activeDeleteItemId,
                onItemLongPress = { item ->
                    activeDeleteItemId = item.id
                },
                onDismissDelete = {
                    activeDeleteItemId = null
                },
                onRemoveItem = { item ->
                    selectedItems = selectedItems.filterNot { it.id == item.id }
                    activeDeleteItemId = null
                },
                onAddSlotClick = {
                    activeDeleteItemId = null
                    onAddClothingClick()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Nagłówek sekcji "Wybrane rzeczy"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Wybrane rzeczy (${selectedItems.size})",
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Dodaj +",
                    color = Accent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            activeDeleteItemId = null
                            onAddClothingClick()
                        }
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Lista wybranych ubrań
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                selectedItems.forEach { item ->
                    SelectedClothingRow(
                        item = item,
                        onSwapClick = {
                            activeDeleteItemId = null
                            onSwapClothingClick(item)
                        },
                        onRemoveClick = {
                            selectedItems = selectedItems.filterNot { it.id == item.id }
                            if (activeDeleteItemId == item.id) {
                                activeDeleteItemId = null
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SaveOutfitButton(
                onClick = {
                    activeDeleteItemId = null
                    onSaveOutfitClick(outfitName.trim(), selectedItems)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        BottomNavigation()
    }
}

@Composable
private fun CreatorHeader() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.rewear_logo),
            contentDescription = "Logo REWEAR",
            modifier = Modifier
                .size(65.dp)
                .align(Alignment.CenterStart)
        )

        Text(
            text = "Kreator stylizacji",
            color = Accent,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.offset(x = 18.dp)
        )
    }
}

@Composable
private fun OutfitNameCard(
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardColor)
            .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Nazwa stylizacji",
                color = TextGrey,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = TextWhite,
                    fontSize = 15.sp
                ),
                cursorBrush = SolidColor(Accent),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edytuj nazwę stylizacji",
            tint = TextGrey,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun OutfitCollageCard(
    items: List<ClothingEntity>,
    activeDeleteItemId: Long?,
    onItemLongPress: (ClothingEntity) -> Unit,
    onDismissDelete: () -> Unit,
    onRemoveItem: (ClothingEntity) -> Unit,
    onAddSlotClick: () -> Unit
) {
    val firstItem = items.getOrNull(0)
    val secondItem = items.getOrNull(1)
    val thirdItem = items.getOrNull(2)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CollageBackground)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Lewy, wysoki kafelek kolażu
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                if (firstItem != null) {
                    CollageItemSlot(
                        item = firstItem,
                        isDeleteVisible = activeDeleteItemId == firstItem.id,
                        onLongPress = { onItemLongPress(firstItem) },
                        onDismissDelete = onDismissDelete,
                        onRemoveClick = { onRemoveItem(firstItem) },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    EmptyAddSlot(
                        onClick = onAddSlotClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Prawa kolumna podzielona na dwa kafelki (góra + dół)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Górny prawy kafelek
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                ) {
                    if (secondItem != null) {
                        CollageItemSlot(
                            item = secondItem,
                            isDeleteVisible = activeDeleteItemId == secondItem.id,
                            onLongPress = { onItemLongPress(secondItem) },
                            onDismissDelete = onDismissDelete,
                            onRemoveClick = { onRemoveItem(secondItem) },
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        EmptyAddSlot(
                            onClick = onAddSlotClick,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // Dolny prawy kafelek
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (thirdItem != null) {
                        CollageItemSlot(
                            item = thirdItem,
                            isDeleteVisible = activeDeleteItemId == thirdItem.id,
                            onLongPress = { onItemLongPress(thirdItem) },
                            onDismissDelete = onDismissDelete,
                            onRemoveClick = { onRemoveItem(thirdItem) },
                            extraCountBadge = (items.size - 3).takeIf { it > 0 },
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        EmptyAddSlot(
                            onClick = onAddSlotClick,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CollageItemSlot(
    item: ClothingEntity,
    isDeleteVisible: Boolean,
    onLongPress: () -> Unit,
    onDismissDelete: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
    extraCountBadge: Int? = null
) {
    val haptic = LocalHapticFeedback.current

    // Płynna animacja przyciemnienia zdjęcia po przytrzymaniu
    val dimAlpha by animateFloatAsState(
        targetValue = if (isDeleteVisible) 0.45f else 0f,
        label = "dimAlpha"
    )

    Box(
        modifier = modifier
    ) {
        // Zdjęcie ubrania + warstwa przyciemniająca
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(14.dp))
                .combinedClickable(
                    onClick = {
                        if (isDeleteVisible) {
                            onDismissDelete()
                        }
                    },
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onLongPress()
                    }
                )
        ) {
            AsyncImage(
                model = item.photo_uri,
                fallback = painterResource(id = R.drawable.no_image_clothing),
                error = painterResource(id = R.drawable.no_image_clothing),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Warstwa przyciemniająca
            if (dimAlpha > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = dimAlpha))
                )
            }

            // Opcjonalny licznik, jeśli w stylizacji jest więcej niż 3 rzeczy
            if (extraCountBadge != null && !isDeleteVisible) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Background.copy(alpha = 0.8f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "+$extraCountBadge",
                        color = TextWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Biały przycisk "Usuń" pojawiający się w prawym dolnym rogu po przytrzymaniu
        AnimatedVisibility(
            visible = isDeleteVisible,
            enter = fadeIn() + scaleIn(initialScale = 0.85f),
            exit = fadeOut() + scaleOut(targetScale = 0.85f),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 14.dp)
                .offset(x = 8.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFFDFCFE),
                shadowElevation = 6.dp,
                modifier = Modifier.clickable { onRemoveClick() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Usuń",
                        color = Background,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Usuń ubranie ze stylizacji",
                        tint = Color(0xFFA794E0),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyAddSlot(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(CollageEmptySlot)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Dodaj ubranie do stylizacji",
            tint = CollagePlusTint,
            modifier = Modifier.size(46.dp)
        )
    }
}

@Composable
private fun SelectedClothingRow(
    item: ClothingEntity,
    onSwapClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardColor)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.photo_uri,
            fallback = painterResource(id = R.drawable.no_image_clothing),
            error = painterResource(id = R.drawable.no_image_clothing),
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = item.name,
            color = TextWhite,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Image(
            painter = painterResource(R.drawable.exchange_icon),
            contentDescription = "Zamień ubranie",
            modifier = Modifier.size(13.dp)
        )
//        Icon(
//            imageVector = Icons.Default.Repeat,
//            contentDescription = "Zamień ubranie",
//            tint = Accent,
//            modifier = Modifier
//                .size(28.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .clickable { onSwapClick() }
//                .padding(4.dp)
//        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Usuń ze stylizacji",
            tint = TextGrey,
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onRemoveClick() }
                .padding(4.dp)
        )
    }
}

@Composable
private fun SaveOutfitButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Accent)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BookmarkVectorIcon(tint = Background)

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "Zapisz stylizację",
            color = Background,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun BookmarkVectorIcon(tint: Color) {
    Canvas(modifier = Modifier.size(16.dp)) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.22f, h * 0.1f)
            lineTo(w * 0.78f, h * 0.1f)
            lineTo(w * 0.78f, h * 0.9f)
            lineTo(w * 0.5f, h * 0.68f)
            lineTo(w * 0.22f, h * 0.9f)
            close()
        }
        drawPath(
            path = path,
            color = tint,
            style = Stroke(
                width = 1.7.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

@Composable
private fun BottomNavigation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(CardColor)
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavigationItem(icon = R.drawable.garderoba, label = "Garderoba", selected = false)
        NavigationItem(icon = R.drawable.kreator, label = "Kreator", selected = true)
        NavigationItem(icon = R.drawable.asystant, label = "Asystent", selected = false)
        NavigationItem(icon = R.drawable.map, label = "Mapa", selected = false)
        NavigationItem(icon = R.drawable.konto, label = "Konto", selected = false)
    }
}

@Composable
private fun NavigationItem(
    icon: Int,
    label: String,
    selected: Boolean
) {
    Column(
        modifier = Modifier.width(62.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 43.dp, height = 29.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(if (selected) LighterCardColor else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(21.dp)
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = label,
            color = if (selected) Accent else TextGrey,
            fontSize = 9.sp
        )
    }
}

// Tymczasowe dane startowe do podglądu i testów ekranu
private fun defaultSampleOutfitItems(): List<ClothingEntity> = listOf(
    ClothingEntity(
        id = 1,
        user_id = 1,
        name = "Koszula lniana",
        photo_uri = "android.resource://com.example.rewear/${R.drawable.koszula}",
        category = "Top",
        style = "Codzienne",
        main_color = "Biały",
        rating = 4
    ),
    ClothingEntity(
        id = 2,
        user_id = 1,
        name = "Jeansy proste",
        photo_uri = "android.resource://com.example.rewear/${R.drawable.jeansy}",
        category = "Spodnie",
        style = "Codzienne",
        main_color = "Niebieski",
        rating = 5
    )
)