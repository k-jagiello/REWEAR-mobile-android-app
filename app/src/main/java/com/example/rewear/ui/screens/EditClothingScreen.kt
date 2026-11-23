package com.example.rewear.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rewear.R
import com.example.rewear.data.local.entity.ClothingEntity
import androidx.compose.ui.text.style.TextOverflow

// Kolory aplikacji REWEAR
private val Background = Color(0xFF242330)
private val CardColor = Color(0xFF302E3E)
private val CardBorder = Color(0xFF3F3C50)
private val LighterCardColor = Color(0xFF443A5D)
private val Accent = Color(0xFFBCB0D0)
private val TextWhite = Color(0xFFF5F1FA)
private val TextGrey = Color(0xFFB5B0C5)

@Composable
fun EditClothingScreen(
    clothing: ClothingEntity = ClothingEntity(
        user_id = 123,
        photo_uri = "android.resource://com.example.rewear/${R.drawable.koszula}",
        name = "Koszula lniana",
        rating = 4,
        spring = true,
        summer = false,
        autumn = true,
        winter = true,
        category = "Topy",
        style = "Codzienne",
        main_color = "Biały",
        colors = arrayListOf()
    ),
    onCameraClick: () -> Unit = {},
    onGalleryClick: () -> Unit = {},
    onSaveClick: (ClothingEntity) -> Unit = {}
) {
    // Stany formularza zainicjalizowane danymi z ClothingEntity
    var name by remember(clothing) { mutableStateOf(clothing.name) }
    var photoUri by remember(clothing) { mutableStateOf(clothing.photo_uri) }
    var category by remember(clothing) { mutableStateOf(clothing.category) }
    var style by remember(clothing) { mutableStateOf(clothing.style) }
    var mainColor by remember(clothing) { mutableStateOf(clothing.main_color) }
    var additionalColors by remember(clothing) { mutableStateOf(clothing.colors.toList()) }

    var spring by remember(clothing) { mutableStateOf(clothing.spring) }
    var summer by remember(clothing) { mutableStateOf(clothing.summer) }
    var autumn by remember(clothing) { mutableStateOf(clothing.autumn) }
    var winter by remember(clothing) { mutableStateOf(clothing.winter) }

    var rating by remember(clothing) { mutableIntStateOf(clothing.rating) }
    var isClean by remember(clothing) { mutableStateOf(clothing.is_clean) }
    var isActive by remember(clothing) { mutableStateOf(clothing.is_active) }

    var showColorDialog by remember { mutableStateOf(false) }

    val availableCategories = listOf("Top", "T-Shirt", "Spodnie", "Spódnica", "Sukienka", "Okrycie wierzchnie", "Buty", "Dodatki")
    val availableStyles = listOf("Codzienne", "Eleganckie", "Sportowe", "Wieczorowe", "Do pracy")
    val availableColors = listOf("Biały", "Czarny", "Beżowy", "Szary", "Niebieski", "Zielony", "Brązowy", "Czerwony", "Różowy", "Fioletowy", "Pomarańczowy", "Turkusowy", "Granatowy")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            EditHeader()

            Spacer(modifier = Modifier.height(20.dp))

            // Sekcja zdjęcia + przyciski Aparat / Galeria
            PhotoEditSection(
                photoUri = photoUri,
                onCameraClick = onCameraClick,
                onGalleryClick = onGalleryClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Pole tekstowe: Nazwa ubrania
            EditableNameCard(
                value = name,
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Lista rozwijana: Kategoria
            DropdownFieldCard(
                label = "Kategoria",
                selectedValue = category.ifBlank { "Wybierz kategorię" },
                options = availableCategories,
                onOptionSelected = { category = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sekcja: Sezony (multi-select)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sezony",
                    color = TextGrey,
                    fontSize = 12.sp
                )
                Text(
                    text = "Możesz wybrać kilka",
                    color = TextGrey,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SeasonToggleChip(
                    label = "Wiosna",
                    selected = spring,
                    onClick = { spring = !spring },
                    modifier = Modifier.weight(1f)
                )
                SeasonToggleChip(
                    label = "Lato",
                    selected = summer,
                    onClick = { summer = !summer },
                    modifier = Modifier.weight(1f)
                )
                SeasonToggleChip(
                    label = "Jesień",
                    selected = autumn,
                    onClick = { autumn = !autumn },
                    modifier = Modifier.weight(1f)
                )
                SeasonToggleChip(
                    label = "Zima",
                    selected = winter,
                    onClick = { winter = !winter },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista rozwijana: Styl
            DropdownFieldCard(
                label = "Styl",
                selectedValue = style.ifBlank { "Wybierz styl" },
                options = availableStyles,
                onOptionSelected = { style = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Wybór kolorów (główny + dodatkowe)
            val colorDisplayText = when {
                mainColor.isBlank() -> "Dodaj kolor główny oraz dodatkowe"
                additionalColors.isEmpty() -> mainColor
                else -> "$mainColor (+${additionalColors.size})"
            }

            ClickableFieldCard(
                label = "Kolory",
                value = colorDisplayText,
                onClick = { showColorDialog = true }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Przełączniki: Czystość i Dostępność
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BinaryToggleSection(
                    title = "Czystość",
                    leftLabel = "Czyste",
                    rightLabel = "Do prania",
                    isLeftSelected = isClean,
                    onSelectionChange = { isClean = it },
                    modifier = Modifier.weight(1f)
                )

                BinaryToggleSection(
                    title = "Dostępność",
                    leftLabel = "Aktywne",
                    rightLabel = "Wyłączone",
                    isLeftSelected = isActive,
                    onSelectionChange = { isActive = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ocena ubrania (1-5 serduszek)
            RatingSection(
                rating = rating,
                onRatingChange = { rating = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Przycisk zapisu zwracający zaktualizowane ClothingEntity
            SaveChangesButton(
                onClick = {
                    val updatedEntity = clothing.copy(
                        name = name.trim(),
                        photo_uri = photoUri,
                        category = category,
                        style = style,
                        main_color = mainColor,
                        colors = ArrayList(additionalColors),
                        spring = spring,
                        summer = summer,
                        autumn = autumn,
                        winter = winter,
                        rating = rating,
                        is_clean = isClean,
                        is_active = isActive
                    )
                    onSaveClick(updatedEntity)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        BottomNavigation()
    }

    // Okno wyboru koloru głównego i kolorów dodatkowych
    if (showColorDialog) {
        ColorPickerDialog(
            availableColors = availableColors,
            selectedMainColor = mainColor,
            selectedAdditionalColors = additionalColors,
            onDismiss = { showColorDialog = false },
            onConfirm = { newMain, newAdditional ->
                mainColor = newMain
                additionalColors = newAdditional
                showColorDialog = false
            }
        )
    }
}

@Composable
private fun EditHeader() {
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.offset(x = 15.dp)
        ) {
            Text(
                text = "Twoja garderoba",
                color = Accent,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Edycja",
                color = TextGrey,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PhotoEditSection(
    photoUri: String?,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = photoUri,
            fallback = painterResource(id = R.drawable.no_image_clothing),
            error = painterResource(id = R.drawable.no_image_clothing),
            contentDescription = "Zdjęcie ubrania",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(105.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(14.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Zdjęcie ubrania",
                color = TextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Jasne tło, jedna rzecz.",
                color = TextGrey,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MediaSourceButton(
                    label = "Aparat",
                    isCamera = true,
                    onClick = onCameraClick,
                    modifier = Modifier.weight(1f)
                )

                MediaSourceButton(
                    label = "Galeria",
                    isCamera = false,
                    onClick = onGalleryClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun MediaSourceButton(
    label: String,
    isCamera: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(CardColor)
            .border(1.dp, CardBorder, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isCamera) {
            CameraVectorIcon(tint = TextWhite)
        } else {
            GalleryVectorIcon(tint = TextWhite)
        }

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = label,
            color = TextWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun EditableNameCard(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardColor)
            .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Nazwa ubrania",
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
}

@Composable
private fun DropdownFieldCard(
    label: String,
    selectedValue: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        ClickableFieldCard(
            label = label,
            value = selectedValue,
            onClick = { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(CardColor)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = if (option == selectedValue) Accent else TextWhite,
                            fontSize = 14.sp
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ClickableFieldCard(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardColor)
            .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                color = TextGrey,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                color = TextWhite,
                fontSize = 15.sp
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = TextGrey,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun SeasonToggleChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (selected) LighterCardColor else CardColor
    val borderColor = if (selected) Accent else CardBorder
    val textColor = if (selected) Accent else TextGrey

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (selected) "$label ✓" else label,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
private fun BinaryToggleSection(
    title: String,
    leftLabel: String,
    rightLabel: String,
    isLeftSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            color = TextGrey,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(CardColor)
                .border(1.dp, CardBorder, RoundedCornerShape(10.dp))
                .padding(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isLeftSelected) LighterCardColor else Color.Transparent)
                    .clickable { onSelectionChange(true) }
                    .padding(vertical = 7.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = leftLabel,
                    color = if (isLeftSelected) Accent else TextGrey,
                    fontSize = 11.sp,
                    fontWeight = if (isLeftSelected) FontWeight.Medium else FontWeight.Normal
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (!isLeftSelected) LighterCardColor else Color.Transparent)
                    .clickable { onSelectionChange(false) }
                    .padding(vertical = 7.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = rightLabel,
                    color = if (!isLeftSelected) Accent else TextGrey,
                    fontSize = 11.sp,
                    fontWeight = if (!isLeftSelected) FontWeight.Medium else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun RatingSection(
    rating: Int,
    onRatingChange: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Jak bardzo lubisz to ubranie?",
            color = TextGrey,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 1..5) {
                val isFilled = i <= rating
                Icon(
                    imageVector = if (isFilled) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Ocena $i",
                    tint = if (isFilled) Accent else TextGrey,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable { onRatingChange(i) }
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "$rating/5",
                color = TextGrey,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun SaveChangesButton(onClick: () -> Unit) {
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
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Background,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Zapisz zmiany",
            color = Background,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ColorPickerDialog(
    availableColors: List<String>,
    selectedMainColor: String,
    selectedAdditionalColors: List<String>,
    onDismiss: () -> Unit,
    onConfirm: (String, List<String>) -> Unit
) {
    var tempMain by remember { mutableStateOf(selectedMainColor) }
    var tempAdditional by remember { mutableStateOf(selectedAdditionalColors) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardColor,
        title = {
            Text(text = "Wybierz kolory", color = TextWhite, fontSize = 18.sp)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Kolor główny:", color = TextGrey, fontSize = 12.sp)
                ColorGrid(
                    colors = availableColors,
                    isSelected = { it == tempMain },
                    onColorClick = { colorName ->
                        tempMain = colorName
                        tempAdditional = tempAdditional - colorName
                    }
                )

                Text(text = "Kolory dodatkowe:", color = TextGrey, fontSize = 12.sp)
                ColorGrid(
                    colors = availableColors.filter { it != tempMain },
                    isSelected = { it in tempAdditional },
                    onColorClick = { colorName ->
                        tempAdditional = if (colorName in tempAdditional) {
                            tempAdditional - colorName
                        } else {
                            tempAdditional + colorName
                        }
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(tempMain, tempAdditional) }) {
                Text(text = "Zatwierdź", color = Accent)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Anuluj", color = TextGrey)
            }
        }
    )
}

@Composable
private fun ColorGrid(
    colors: List<String>,
    isSelected: (String) -> Boolean,
    onColorClick: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        colors.chunked(3).forEach { rowColors ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                rowColors.forEach { colorName ->
                    val selected = isSelected(colorName)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selected) LighterCardColor else Background)
                            .border(
                                width = 1.dp,
                                color = if (selected) Accent else CardBorder,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onColorClick(colorName) }
                            .padding(horizontal = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = colorName,
                            color = if (selected) Accent else TextGrey,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Dopełnia brakujące kolumny niewidocznymi elementami o tej samej wadze
                repeat(3 - rowColors.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun CameraVectorIcon(tint: Color) {
    Canvas(modifier = Modifier.size(16.dp)) {
        val w = size.width
        val h = size.height
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.08f, h * 0.25f),
            size = Size(w * 0.84f, h * 0.62f),
            cornerRadius = CornerRadius(w * 0.14f, h * 0.14f),
            style = Stroke(width = 1.6.dp.toPx())
        )
        drawCircle(
            color = tint,
            radius = w * 0.17f,
            center = Offset(w * 0.5f, h * 0.56f),
            style = Stroke(width = 1.6.dp.toPx())
        )
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.34f, h * 0.13f),
            size = Size(w * 0.32f, h * 0.12f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )
    }
}

@Composable
private fun GalleryVectorIcon(tint: Color) {
    Canvas(modifier = Modifier.size(16.dp)) {
        val w = size.width
        val h = size.height
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.1f, h * 0.15f),
            size = Size(w * 0.8f, h * 0.7f),
            cornerRadius = CornerRadius(w * 0.14f, h * 0.14f),
            style = Stroke(width = 1.6.dp.toPx())
        )
        drawCircle(
            color = tint,
            radius = w * 0.09f,
            center = Offset(w * 0.33f, h * 0.38f)
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.2f, h * 0.72f),
            end = Offset(w * 0.45f, h * 0.52f),
            strokeWidth = 1.6.dp.toPx()
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.45f, h * 0.52f),
            end = Offset(w * 0.62f, h * 0.64f),
            strokeWidth = 1.6.dp.toPx()
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.55f, h * 0.6f),
            end = Offset(w * 0.78f, h * 0.44f),
            strokeWidth = 1.6.dp.toPx()
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
        NavigationItem(icon = R.drawable.garderoba, label = "Garderoba", selected = true)
        NavigationItem(icon = R.drawable.kreator, label = "Kreator", selected = false)
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