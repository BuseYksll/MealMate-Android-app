package com.example.mealmate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.R
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.activity.compose.BackHandler

@Composable
fun RecipeDetailScreen(
    imageRes: Int,
    title: String,
    author: String,
    rating: Double,
    ratingsCount: Int,
    description: String,
    prepTime: String,
    cookTime: String,
    totalTime: String,
    servings: String,
    calories: String,
    fat: String,
    carbs: String,
    protein: String,
    onBack: () -> Unit,
    ingredients: List<String>,
    instructions: List<String>
) {
    BackHandler {
        onBack()
    }
    var selectedTab by remember { mutableStateOf(0) } // 0: Malzemeler, 1: Talimatlar
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left_arrow),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, shape = CircleShape)
                        .clickable { onBack() }
                        .padding(4.dp)
                )
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "Share",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color.White, shape = CircleShape)
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bookmark),
                        contentDescription = "Bookmark",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color.White, shape = CircleShape)
                            .padding(4.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Tarif bilgileri için kırmızı yuvarlak dikdörtgen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = (-32).dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFE11932), shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 20.dp, horizontal = 24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tarif",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "by $author",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Puan satırı
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(rating.toInt()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        tint = Color(0xFFE11932),
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = rating.toString(), color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cook),
                    contentDescription = "MealMate",
                    tint = Color(0xFFE11932),
                    modifier = Modifier.size(28.dp)
                )
                Text(text = "MealMate ", color = Color.Gray, fontSize = 13.sp)
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$ratingsCount puan", color = Color.Gray, fontSize = 13.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Açıklama
        Text(
            text = description,
            color = Color.Black,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        // Kırmızı alt çizgili süre bölümü
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp)
            ) {
                Text(
                    text = "Süre",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(Color(0xFFE11932), shape = RoundedCornerShape(2.dp))
                        .align(Alignment.BottomStart)
                ) {}
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().background(Color.White, shape = RoundedCornerShape(12.dp)).border(1.dp, Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp)).padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Hazırlık S :", color = Color.Gray, fontSize = 13.sp)
                    Text(prepTime, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Pişirme S:", color = Color.Gray, fontSize = 13.sp)
                    Text(cookTime, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Toplam S :", color = Color.Gray, fontSize = 13.sp)
                    Text(totalTime, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Porsiyon :", color = Color.Gray, fontSize = 13.sp)
                    Text(servings, fontWeight = FontWeight.Bold)
                }
            }
        }
        // Besin Değerleri bölümü
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Besin Degereleri ",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8F8F8), shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Kalori :", color = Color.Gray, fontSize = 13.sp)
                    Text(calories, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Yağ :", color = Color.Gray, fontSize = 13.sp)
                    Text(fat, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Karbs :", color = Color.Gray, fontSize = 13.sp)
                    Text(carbs, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Protein :", color = Color.Gray, fontSize = 13.sp)
                    Text(protein, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        // Malzemeler ve Talimatlar için sekmeler
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color(0xFFE11932),
            indicator = { tabPositions: List<TabPosition> ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = Color(0xFFE11932)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Malzemeler", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Talimatlar", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (selectedTab == 0) {
            // Malzemeler Listesi bölümü
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Malzemeler",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF8F8F8), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    ingredients.forEach { ingredient ->
                        if (ingredient == "---") {
                            Spacer(modifier = Modifier.height(8.dp))
                        } else if (ingredient.endsWith(":")) {
                            Text(ingredient, fontWeight = FontWeight.Bold, color = Color(0xFFE11932), fontSize = 15.sp, modifier = Modifier.padding(vertical = 4.dp))
                        } else {
                            Text("• $ingredient", fontSize = 15.sp, color = Color.Black, modifier = Modifier.padding(vertical = 2.dp))
                        }
                    }
                }
            }
        } else {
            // Talimatlar bölümü
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Adım Adım Talimatlar",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                instructions.forEachIndexed { idx, step ->
                    val stepImageRes = when (title) {
                        "Mercimek Corbasi", "Mercimek Çorbası (Lokanta Usulü)" -> when (idx + 1) {
                            1 -> R.drawable.mercimek_corbasi_adim_1
                            2 -> R.drawable.mercimek_corbasi_adim_2
                            3 -> R.drawable.mercimek_corbasi_adim_3
                            4 -> R.drawable.mercimek_corbasi_adim_4
                            5 -> R.drawable.mercimek_corbasi_adim_5
                            6 -> R.drawable.mercimek_corbasi_adim_6
                            7 -> R.drawable.mercimek_corbasi_adim_7
                            8 -> R.drawable.mercimek_corbasi_adim_8
                            else -> R.drawable.mercimek_corbasi
                        }
                        "Spoonful" -> when (idx + 1) {
                            1 -> R.drawable.spoonful_asama_1
                            2 -> R.drawable.spoonful_asama_2
                            3 -> R.drawable.spoonful_asama_3
                            4 -> R.drawable.spoonful_asama_4
                            5 -> R.drawable.spoonful_asama_5
                            6 -> R.drawable.spoonful_asama_6
                            7 -> R.drawable.spoonful_asama_7
                            else -> R.drawable.spoonful
                        }
                        "Tavuk Sote" -> when (idx + 1) {
                            1 -> R.drawable.tavuk_sote_1
                            2 -> R.drawable.tavuk_sote_2
                            3 -> R.drawable.tavuk_sote_3
                            4 -> R.drawable.tavuk_sote_4
                            5 -> R.drawable.tavuk_sote_5
                            6 -> R.drawable.tavuk_sote_6
                            else -> R.drawable.tavuk_sote
                        }
                        "Arnavut Ciğeri" -> when (idx + 1) {
                            1 -> R.drawable.arnavut_cigeri_1
                            2 -> R.drawable.arnavut_cigeri_2
                            3 -> R.drawable.arnavut_cigeri_3
                            4 -> R.drawable.arnavut_cigeri_4
                            5 -> R.drawable.arnavut_cigeri_5
                            6 -> R.drawable.arnavut_cigeri_6
                            else -> R.drawable.arnavut_cigeri
                        }
                        "Lahmacun" -> when (idx + 1) {
                            1 -> R.drawable.lahmacun_1
                            2 -> R.drawable.lahmacun_2
                            3 -> R.drawable.lahmacun_3
                            4 -> R.drawable.lahmacun_4
                            5 -> R.drawable.lahmacun_5
                            6 -> R.drawable.lahmacun_6
                            7 -> R.drawable.lahmacun_7
                            8 -> R.drawable.lahmacun_8
                            9 -> R.drawable.lahmacun_9
                            10 -> R.drawable.lahmacun_10
                            11 -> R.drawable.lahmacun_11
                            else -> R.drawable.lahmacun
                        }
                        "Domates Çorbası" -> when (idx + 1) {
                            1 -> R.drawable.domates_corbasi_adim1
                            2 -> R.drawable.domates_corbasi_adim2
                            3 -> R.drawable.domates_corbasi_adim3
                            4 -> R.drawable.domates_corbasi_adim4
                            5 -> R.drawable.domates_corbasi_adim5
                            else -> R.drawable.domates_corbasi
                        }
                        else -> imageRes
                    }
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = stepImageRes),
                                contentDescription = "Step ${idx + 1}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                            Text(
                                text = "${idx + 1}. $step",
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
} 