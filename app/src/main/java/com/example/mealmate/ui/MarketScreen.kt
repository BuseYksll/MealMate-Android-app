package com.example.mealmate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.R
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.activity.compose.BackHandler

val groceryCategories = listOf(
    "Meyve", "Sebzeler", "Süt & Yumurta", "Et", "Baharatlar", "Tahıllar", "Yağlar", "Diğerleri"
)

val groceryData = mapOf(
    "Süt & Yumurta" to listOf("kaşar peyniri", "krema", "süt", "sütlü çikolata", "tereyağı", "yumurta"),
    "Baharatlar" to listOf("biber salçası", "Bulyon", "karabiber", "karbonat", "kuru nane", "pul biber", "sumak", "toz kişniş", "toz şeker", "tuz", "vanilin"),
    "Tahıllar" to listOf("kek", "lahmacun hamuru", "un"),
    "Yağlar" to listOf("sıvı yağ"),
    "Et" to listOf("ciğer", "tavuk göğsü", "yağlı dana kıyma"),
    "Sebzeler" to listOf("domates", "domates salçası", "havuç", "kırmızı biber", "kırmızı soğan", "maydanoz", "patates", "soğan", "toz kırmızı biber", "yeşil biber"),
    "Diğerleri" to listOf("nişasta", "su", "kırmızı mercimek", "sarı mercimek"),
    "Meyve" to listOf("Apple", "Avocado", "Banana", "Blueberry", "Cherry", "Grapefruit", "Kiwi", "Lemon", "Ananas")
)

val groceryColors = listOf(
    Color(0xFFFFF3F6), Color(0xFFE6F7E6), Color(0xFFFFF9E6), Color(0xFFE6F0FF), Color(0xFFFFE6F0), Color(0xFFF6E6FF)
)

@Composable
fun MarketScreen(
    onConfirm: (List<String>) -> Unit,
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }
    var selectedCategory by remember { mutableStateOf(groceryCategories.first()) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val items = groceryData[selectedCategory] ?: emptyList()
    var selectedItems by remember { mutableStateOf(setOf<String>()) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        // Geri butonu ve başlık ile üst kısım
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_left_arrow),
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFFF8F8F8), shape = CircleShape)
                    .clickable { onBack() }
                    .padding(4.dp)
            )
            Text(
                text = "Market",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(40.dp))
        }
        
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState())
        ) {
            //Text(
            //   text = "Market",
            //    fontWeight = FontWeight.Bold,
            //   fontSize = 22.sp,
            //   color = Color.Black,
            //   modifier = Modifier.padding(bottom = 12.dp)
            // )
            // Arama çubuğu
            Surface(
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 2.dp,
                color = Color(0xFFF8F8F8),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = Color(0xFF888888),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    androidx.compose.material3.TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search to add ingredients", color = Color(0xFF888888), fontSize = 15.sp) },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "Camera",
                        tint = Color(0xFFE11932),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Kategori sekmeleri
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                groceryCategories.forEach { category ->
                    val selected = category == selectedCategory
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = if (selected) Color(0xFFE11932) else Color.White,
                        border = if (selected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE11932)),
                        modifier = Modifier
                            .height(36.dp)
                            .clickable { selectedCategory = category }
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 18.dp)) {
                            Text(
                                text = category,
                                color = if (selected) Color.White else Color(0xFFE11932),
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Malzeme ızgarası
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items.filter { it.contains(searchQuery.text, ignoreCase = true) }) { item ->
                    val isSelected = selectedItems.contains(item)
                    val imageRes = when (item.lowercase()) {
                        "apple" -> R.drawable.elma
                        "avocado" -> R.drawable.avakado
                        "banana" -> R.drawable.muz
                        "blueberry" -> R.drawable.blueberry
                        "cherry" -> R.drawable.cherry
                        "grapefruit" -> R.drawable.grape
                        "kiwi" -> R.drawable.kivi
                        "lemon" -> R.drawable.lemon
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
                    Box(
                        modifier = Modifier
                            .aspectRatio(0.8f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .clickable {
                                selectedItems = if (isSelected) selectedItems - item else selectedItems + item
                            }
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Resim ve seçim dairesi ile üst beyaz alan
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = imageRes),
                                    contentDescription = item,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .align(Alignment.Center)
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                        .size(24.dp)
                                        .background(Color.White, shape = CircleShape)
                                        .border(2.dp, Color(0xFFE11932), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_tick),
                                            contentDescription = "Selected",
                                            tint = Color(0xFFE11932),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                            // Malzeme adı ile alt açık pembe alan
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFFFF3F6), shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.replaceFirstChar { it.uppercase() },
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
            // Onay butonu
            Button(
                onClick = {
                    onConfirm(selectedItems.toList())
                    selectedItems = emptySet()
                },
                enabled = selectedItems.isNotEmpty(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedItems.isNotEmpty()) Color(0xFFE11932) else Color(0xFF888888)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(top = 12.dp)
            ) {
                Text(text = "Seçimi Onayla", color = Color.White, fontSize = 18.sp)
            }
        }
    }
} 