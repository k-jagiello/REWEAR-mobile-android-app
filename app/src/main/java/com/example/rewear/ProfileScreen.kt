
package com.example.rewear.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.res.painterResource
import com.example.rewear.R

// Kolory aplikacji REWEAR
private val Background = Color(0xFF242330)
private val CardColor = Color(0xFF302E3E)
private val Accent = Color(0xFFBCB0D0)
private val TextWhite = Color(0xFFF5F1FA)
private val TextGrey = Color(0xFFB5B0C5)

@Composable
fun ProfileScreen() {

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

            ProfileHeader()

            Spacer(modifier = Modifier.height(24.dp))

            ProfileCard()

            Spacer(modifier = Modifier.height(26.dp))

            SectionTitle(
                title = "Zapisane stylizacje",
                count = 0
            )

            Spacer(modifier = Modifier.height(12.dp))

            EmptySection(
                message = "Nie masz jeszcze zapisanych stylizacji",
                description = "Utworz swoja pierwsza stylizacje"
            )

            Spacer(modifier = Modifier.height(26.dp))

            SectionTitle(
                title = "Rzeczy do prania",
                count = 0
            )

            Spacer(modifier = Modifier.height(12.dp))

            EmptySection(
                message = "Brak rzeczy do prania",
                description = "Twoje ubrania sa czyste"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        BottomNavigation()
    }
}

@Composable
private fun ProfileHeader() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.rewear_logo),
            contentDescription = "Logo REWEAR",
            modifier = Modifier.size(65.dp)
        )

        Column {

            Text(
                text = "Twoje konto",
                color = Accent,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Mala garderoba. Wiele mozliwosci.",
                color = TextGrey,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun ProfileCard() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CardColor)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF443A5D)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Zdjecie profilowe",
                    tint = Accent,
                    modifier = Modifier.size(25.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Uzupelnij profil",
                    color = TextWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Dodaj swoje dane",
                    color = TextGrey,
                    fontSize = 11.sp
                )
            }

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edytuj profil",
                tint = Accent,
                modifier = Modifier.size(19.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ProfileStatistic("0", "ubran")
            ProfileStatistic("0", "stylizacje")
            ProfileStatistic("0", "znajomych")
        }
    }
}

@Composable
private fun ProfileStatistic(
    number: String,
    label: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(85.dp)
    ) {

        Text(
            text = number,
            color = TextWhite,
            fontSize = 19.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = label,
            color = TextGrey,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun SectionTitle(
    title: String,
    count: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            color = TextWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "Wszystkie ($count)",
            color = Accent,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun EmptySection(
    message: String,
    description: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(172.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(CardColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = Color(0xFF8F88A7),
            modifier = Modifier.size(45.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = message,
            color = TextWhite,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = description,
            color = TextGrey,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
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
            selected = false
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
            selected = true
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
                    if (selected) Color(0xFF493C68)
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

