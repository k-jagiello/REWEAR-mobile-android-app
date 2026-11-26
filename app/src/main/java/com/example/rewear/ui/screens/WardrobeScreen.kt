package com.example.rewear.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.res.painterResource
import com.example.rewear.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage

// Kolory aplikacji REWEAR
private val Background = Color(0xFF242330)
private val CardColor = Color(0xFF302E3E)
private val LighterCardColor = Color(0xFF443A5D)
private val Accent = Color(0xFFBCB0D0)
private val TextWhite = Color(0xFFF5F1FA)
private val TextGrey = Color(0xFFB5B0C5)

@Composable
fun WardrobeScreen() {

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

            WardrobeHeader()

            Spacer(modifier = Modifier.height(24.dp))

            Searchbar()

            Spacer(modifier = Modifier.height(26.dp))

            FilteringPanel("Góry",0)

            Spacer(modifier = Modifier.height(26.dp))

            //jak będziemy już łączyć z bazą to tu trzeba będzie wykonać konwersję z clothingEntity
            //na clothingItem (fun w Converters)
            val sampleClothes = listOf(
                ClothingItem("android.resource://com.example.rewear/${R.drawable.koszula}","Koszula lniana", ClothingStatus.CLEAN_ACTIVE),
                ClothingItem(null,name = "Sukienka oliwkowa", status = ClothingStatus.INACTIVE)
            )

            WardrobeGrid(items = sampleClothes)

            Spacer(modifier = Modifier.height(16.dp))

            AddClothingButton(onClick = { /* akcja dodawania */ })
        }

        BottomNavigation()
    }
}

@Composable
private fun WardrobeHeader() {

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
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun Searchbar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardColor)
            .padding(14.dp)
    ){

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(CardColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Lupa wyszukiwania",
                    tint = TextWhite,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Szukaj w swojej garderobie",
                    color = TextWhite,
                    fontSize = 13.sp,
                    //fontWeight = FontWeight.Bold
                )
            }
        }
    }

}

@Composable
private fun FilteringPanel(
    mostPopular: String,
    count: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterChip(
            label = "Wszystkie ($count)",
            selected = true
        )

        FilterChip(
            label = mostPopular,
            selected = false
        )

        FilterChip(
            label = "Filtry",
            selected = false,
            icon = R.drawable.filter_icon
        )
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    icon: Int? = null
) {
    val backgroundColor = if (selected) LighterCardColor else CardColor
    val contentColor = if (selected) Accent else TextWhite

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = contentColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 14.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }

        Text(
            text = label,
            color = contentColor,
            fontSize = 11.sp
        )
    }
}

enum class ClothingStatus(
    val dotColor: Color,
    val primaryText: String,
    val primaryTextColor: Color,
    val secondaryText: String? = null
) {
    CLEAN_ACTIVE(
        dotColor = Color(0xFF8BB89E),
        primaryText = "Czyste",
        primaryTextColor = Color(0xFF8BB89E),
        secondaryText = " · aktywne"
    ),
    TO_WASH(
        dotColor = Color(0xFFD6A87C),
        primaryText = "Do prania",
        primaryTextColor = Color(0xFFD6A87C)
    ),
    INACTIVE(
        dotColor = Color(0xFF9E99B0),
        primaryText = "Wyłączone",
        primaryTextColor = Color(0xFF9E99B0)
    )
}

class ClothingItem(
    val image: String?,
    val name: String,
    val status: ClothingStatus
)

@Composable
private fun WardrobeGrid(
    items: List<ClothingItem>
){
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { item ->
                    ClothingCard(
                        item = item,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Jeśli liczba ubrań jest nieparzysta, pusty element wyrówna szerokość ostatniej karty
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ClothingCard(
    item: ClothingItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardColor)
            .padding(10.dp)
    ) {
        AsyncImage(
            model = item.image, // może być Stringiem albo nullem
            fallback = painterResource(id = R.drawable.no_image_clothing), // wyświetli się, gdy imageUri == null
            error = painterResource(id = R.drawable.no_image_clothing),    // wyświetli się, gdy plik zniknie z telefonu
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.25f)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = item.name,
            color = TextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(item.status.dotColor)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = item.status.primaryText,
                color = item.status.primaryTextColor,
                fontSize = 11.sp
            )

            if (item.status.secondaryText != null) {
                Text(
                    text = item.status.secondaryText,
                    color = item.status.primaryTextColor,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun AddClothingButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Accent)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = Background,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Dodaj ubranie",
            color = Background,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
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

        NavigationItem(
            icon = R.drawable.garderoba,
            label = "Garderoba",
            selected = true
        )

        NavigationItem(
            icon = R.drawable.kreator,
            label = "Kreator",
            selected = false
        )

        NavigationItem(
            icon = R.drawable.asystant,
            label = "Asystent",
            selected = false
        )

        NavigationItem(
            icon = R.drawable.map,
            label = "Mapa",
            selected = false
        )

        NavigationItem(
            icon = R.drawable.konto,
            label = "Konto",
            selected = false
        )
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
                .background(
                    if (selected) LighterCardColor
                    else Color.Transparent
                ),
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

