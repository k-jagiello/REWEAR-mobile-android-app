package com.example.rewear.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rewear.R
import com.example.rewear.data.local.entity.ClothingEntity

// Kolory aplikacji REWEAR
private val Background = Color(0xFF242330)
private val CardColor = Color(0xFF302E3E)
private val LighterCardColor = Color(0xFF443A5D)
private val Accent = Color(0xFFBCB0D0)
private val TextWhite = Color(0xFFF5F1FA)
private val TextGrey = Color(0xFFB5B0C5)

data class ClothingDetails(
    val photo_uri: String?,
    val name: String,
    val rating: Int,
    val seasons: List<String>,
    val category: String,
    val style: String,
    val main_color: String,
    val colors: ArrayList<String> = arrayListOf()
)

@Composable
fun ClothingDetailsScreen(
    // to jest oczywiście przykładowa dana -> potem trzeba będzie tu operować raczej samym
    // clothing_id i na tej podstawie pobierać pozostałe dane
    clothing: ClothingEntity = ClothingEntity(
        user_id = 123,
        photo_uri = "android.resource://com.example.rewear/${R.drawable.koszula}",
        name = "Koszula lniana",
        rating = 4,
        spring = true,
        summer = false,
        autumn = true,
        winter = true,
        category = "Góry",
        style = "Codzienne",
        main_color = "Biały",
        colors = arrayListOf()
    ),
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
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

            DetailsHeader()

            Spacer(modifier = Modifier.height(20.dp))

            BackButton(onClick = onBackClick)

            Spacer(modifier = Modifier.height(18.dp))

            ClothingMainCard(
                image = clothing.photo_uri,
                name = clothing.name,
                rating = clothing.rating
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Podstawowe informacje",
                color = TextGrey,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            SeasonsRow(clothing.spring,clothing.summer, clothing.autumn, clothing.winter)

            Spacer(modifier = Modifier.height(18.dp))

            CategoryAndOccasionRow(
                category = clothing.category,
                occasion = clothing.style
            )

            Spacer(modifier = Modifier.height(18.dp))

            ColorsCard(
                primaryColor = clothing.main_color,
                additionalColors = clothing.colors
            )

            Spacer(modifier = Modifier.height(20.dp))

            EditButton(onClick = onEditClick)

            Spacer(modifier = Modifier.height(24.dp))
        }

        BottomNavigation()
    }
}

@Composable
private fun DetailsHeader() {
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
                text = "Informacje o ubraniach",
                color = TextGrey,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Accent)
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Powrót",
            tint = Background,
            modifier = Modifier
                .size(22.dp)
                .align(Alignment.CenterStart)
        )

        Text(
            text = "Powrót",
            color = Background,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ClothingMainCard(
    image: String?,
    name: String,
    rating: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardColor)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = image,
            fallback = painterResource(id = R.drawable.no_image_clothing),
            error = painterResource(id = R.drawable.no_image_clothing),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(110.dp)
                .height(145.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                for (i in 1..5) {
                    val isFilled = i <= rating
                    Icon(
                        imageVector = if (isFilled) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFilled) Accent else TextGrey,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "$rating/5",
                    color = TextGrey,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun SeasonsRow(spring: Boolean, summer: Boolean, autumn: Boolean, winter: Boolean) {
    val seasons = listOf("Wiosna", "Lato", "Jesień", "Zima")
    val boolVals = listOf(spring,summer,autumn,winter)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        seasons.forEachIndexed { index, season ->
            if(boolVals[index]) {
                InfoPill(
                    text = season,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun CategoryAndOccasionRow(
    category: String,
    occasion: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HighlightBox(
            text = category,
            modifier = Modifier.weight(1f)
        )

        HighlightBox(
            text = occasion,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun HighlightBox(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Accent),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = TextWhite,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ColorsCard(
    primaryColor: String,
    additionalColors: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kolory:",
                color = TextGrey,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.width(24.dp))

            InfoPill(
                text = primaryColor,
                modifier = Modifier.widthIn(min = 140.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (additionalColors.isEmpty()) {
            InfoPill(
                text = "Brak kolorów dodatkowych",
                modifier = Modifier.fillMaxWidth(0.85f)
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                additionalColors.forEach { colorName ->
                    InfoPill(text = colorName)
                }
            }
        }
    }
}

@Composable
private fun InfoPill(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(LighterCardColor)
            .border(
                width = 1.dp,
                color = Accent,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = TextGrey,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EditButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Accent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Edytuj",
            color = Background,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
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