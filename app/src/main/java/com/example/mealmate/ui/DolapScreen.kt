package com.example.mealmate.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import com.example.mealmate.R
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.activity.compose.BackHandler

@Composable
fun DolapScreen(
    onBack: () -> Unit,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    dolapItems: List<String>,
    onRemoveItem: (String) -> Unit = {}
) {
    BackHandler {
        onBack()
    }
    // Sadece alt navigasyon çubuğu için MainScaffold kullan, selamlama veya eylemler için değil
    MainScaffold(
        selectedTab = selectedTab,
        onTabSelected = onTabSelected,
        showBack = false,
        modifier = modifier,
        showGreeting = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Özel başlık: geri butonu + Dolap başlığı
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left_arrow),
                    contentDescription = "Geri",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFF8F8F8), shape = CircleShape)
                        .clickable { onBack() }
                        .padding(4.dp)
                )
                Text(
                    text = "Dolap",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.size(40.dp))
            }
            // İçerik alanı (boş durum veya dolap öğeleri)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (dolapItems.isEmpty()) {
                    Text(
                        text = "Mevcut Malzemeler",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.my_grocery_list),
                        contentDescription = "Alışveriş İllüstrasyonu",
                        modifier = Modifier
                            .height(220.dp)
                            .padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Mevcut alışveriş listeniz yok",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Text(
                        text = "Dolabınızda her bir şey YOK!",
                        color = Color.Gray,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                } else {
                    dolapItems.forEach { item ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(2.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val imageRes = when (item.lowercase()) {
                                    "elma" -> R.drawable.elma
                                    "avokado" -> R.drawable.avakado
                                    "muz" -> R.drawable.muz
                                    "yaban mersini" -> R.drawable.blueberry
                                    "kiraz" -> R.drawable.cherry
                                    "üzüm" -> R.drawable.grape
                                    "kivi" -> R.drawable.kivi
                                    "limon" -> R.drawable.lemon
                                    "ananas" -> R.drawable.ananas
                                    "kaşar peyniri" -> R.drawable.kasar
                                    "krema" -> R.drawable.krema
                                    "süt" -> R.drawable.sut
                                    "sütlü çikolata" -> R.drawable.cikolatali_sut
                                    "tereyağı" -> R.drawable.tereyag
                                    "yumurta" -> R.drawable.yumurta
                                    "biber salçası" -> R.drawable.biber_salcasi
                                    "bulyon" -> R.drawable.suyu_bulyon
                                    "karabiber" -> R.drawable.karabiber
                                    "karbonat" -> R.drawable.karbonat
                                    "kuru nane" -> R.drawable.kuru_nane
                                    "pul biber" -> R.drawable.pulbiber
                                    "sumak" -> R.drawable.sumak
                                    "toz kişniş" -> R.drawable.toz_kesnes
                                    "toz şeker" -> R.drawable.toz_seker
                                    "tuz" -> R.drawable.tuz
                                    "vanilin" -> R.drawable.vanilin
                                    "kek" -> R.drawable.kek
                                    "lahmacun hamuru" -> R.drawable.lahmacun_hamuru
                                    "un" -> R.drawable.un
                                    "sıvı yağ" -> R.drawable.yag
                                    "ciğer" -> R.drawable.ciger
                                    "tavuk göğsü" -> R.drawable.tavuk
                                    "yağlı dana kıyma" -> R.drawable.kiyma
                                    "domates" -> R.drawable.domates
                                    "domates salçası" -> R.drawable.domates_salcasi
                                    "havuç" -> R.drawable.havuc
                                    "kırmızı biber" -> R.drawable.kirmizi_biber
                                    "kırmızı soğan" -> R.drawable.kirmizi_sogan
                                    "maydanoz" -> R.drawable.maydanoz
                                    "patates" -> R.drawable.patates
                                    "soğan" -> R.drawable.sogan
                                    "toz kırmızı biber" -> R.drawable.toz_kirmizibiber
                                    "yeşil biber" -> R.drawable.yesil_biber
                                    "nişasta" -> R.drawable.nisasta
                                    "su" -> R.drawable.su
                                    "kırmızı mercimek" -> R.drawable.kirmizi_mercimek
                                    "sarı mercimek" -> R.drawable.sari_mercimek
                                    else -> R.drawable.ic_launcher_foreground
                                }
                                Image(
                                    painter = painterResource(id = imageRes),
                                    contentDescription = item,
                                    modifier = Modifier
                                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                        .size(40.dp)
                                )
                                Text(
                                    text = item.replaceFirstChar { it.uppercase() },
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp,
                                    modifier = Modifier.weight(1f).padding(start = 16.dp)
                                )
                                IconButton(onClick = { onRemoveItem(item) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Kaldır",
                                        tint = Color(0xFFE11932)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}