package com.example.mealmate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun OnboardingScreen(onFinish: () -> Unit, onSkip: () -> Unit) {
    val pages = listOf(
        OnboardingPage(
            imageRes = R.drawable.plan_meals,
            title = "Yemek Planla",
            description = "Tercihlerinize, diyet ihtiyaçlarınıza ve mevcut malzemelerinize göre kişiselleştirilmiş yemek planları oluşturun."
        ),
        OnboardingPage(
            imageRes = R.drawable.create_grosery_list,
            title = "Alışveriş Listesi Oluştur",
            description = "İstediğiniz, düzenli alışveriş listenizi oluşturun ve kolayca alışveriş yapın."
        ),
        OnboardingPage(
            imageRes = R.drawable.discover_new_recipes,
            title = "Yeni Tarifler Keşfet",
            description = "Parmaklarınızın ucunda bir lezzet dünyasını keşfedin ve damak tadınıza göre özelleştirin."
        )
    )
    var page by remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = pages[page].imageRes),
                    contentDescription = pages[page].title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.2f)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = pages[page].title,
                    color = Color(0xFFE11932),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = pages[page].description,
                    color = Color(0xFF888888),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    pages.forEachIndexed { idx, _ ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (page == idx) 10.dp else 8.dp)
                                .background(
                                    color = if (page == idx) Color(0xFFE11932) else Color(0xFFDDDDDD),
                                    shape = RoundedCornerShape(50)
                                )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        if (page < pages.lastIndex) page++ else onFinish()
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11932)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .padding(bottom = 32.dp)
                ) {
                    Text(text = if (page < pages.lastIndex) "İleri" else "Başla", fontSize = 18.sp, color = Color.White)
                }
            }
        }
        Text(
            text = "Atla",
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 32.dp, end = 24.dp)
                .clickable { onSkip() }
        )
    }
}

data class OnboardingPage(val imageRes: Int, val title: String, val description: String) 