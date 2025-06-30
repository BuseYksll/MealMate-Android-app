package com.example.mealmate.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.runtime.setValue

data class RecipeDetail(
    val imageRes: Int,
    val title: String,
    val author: String,
    val rating: Double,
    val ratingsCount: Int,
    val description: String,
    val prepTime: String,
    val cookTime: String,
    val totalTime: String,
    val servings: String,
    val calories: String,
    val fat: String,
    val carbs: String,
    val protein: String,
    val ingredients: List<String>,
    val instructions: List<String>
)

@Composable
fun MainScaffold(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    showBack: Boolean = false,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    showGreeting: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Üst Başlık Satırı (sadece showGreeting true ise)
        if (showGreeting) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBack && onBack != null) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_right_arrow),
                    contentDescription = "Back",
                    tint = Color(0xFFE11932),
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onBack() }
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Text(
                    text = "Merhaba, Buse\uD83D\uDC69\u200D\uD83C\uDF73", // 👩‍🍳
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            // Yuvarlak dikdörtgen içinde artı ikonu
            Surface(
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 4.dp,
                color = Color.White,
                modifier = Modifier.size(width = 40.dp, height = 40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Ekle",
                    tint = Color(0xFFE11932),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .clickable { /* TODO: Add action */ }
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            // Yuvarlak dikdörtgen içinde menü ikonu
            Surface(
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 4.dp,
                color = Color.White,
                modifier = Modifier.size(width = 40.dp, height = 40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "Menü",
                    tint = Color(0xFFE11932),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .clickable { /* TODO: Menu action */ }
                )
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        }
        // Ana içerik
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
        // Alt Navigasyon Çubuğu
        Surface(
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 8.dp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarItem(
                    iconRes = R.drawable.ic_cook,
                    label = "Cook",
                    selected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )
                BottomBarItem(
                    iconRes = R.drawable.ic_market,
                    label = "Market",
                    selected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )
                BottomBarItem(
                    iconRes = R.drawable.ic_dolap,
                    label = "Dolap",
                    selected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )
            }
        }
    }
}

@Composable
fun BottomBarItem(iconRes: Int, label: String, selected: Boolean, onClick: () -> Unit) {
    val activeColor = Color(0xFFE11932)
    val inactiveColor = Color(0xFF888888)
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = if (selected) activeColor else inactiveColor,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = if (selected) activeColor else inactiveColor,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 15.sp
        )
    }
}

@Composable
fun MainScreen(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    dolapItems: List<String> = emptyList()
) {
    var showRecipeDetail by remember { mutableStateOf(false) }
    var selectedRecipeDetail by remember { mutableStateOf<RecipeDetail?>(null) }
    if (showRecipeDetail && selectedRecipeDetail != null) {
        RecipeDetailScreen(
            imageRes = selectedRecipeDetail!!.imageRes,
            title = selectedRecipeDetail!!.title,
            author = selectedRecipeDetail!!.author,
            rating = selectedRecipeDetail!!.rating,
            ratingsCount = selectedRecipeDetail!!.ratingsCount,
            description = selectedRecipeDetail!!.description,
            prepTime = selectedRecipeDetail!!.prepTime,
            cookTime = selectedRecipeDetail!!.cookTime,
            totalTime = selectedRecipeDetail!!.totalTime,
            servings = selectedRecipeDetail!!.servings,
            calories = selectedRecipeDetail!!.calories,
            fat = selectedRecipeDetail!!.fat,
            carbs = selectedRecipeDetail!!.carbs,
            protein = selectedRecipeDetail!!.protein,
            onBack = { showRecipeDetail = false },
            ingredients = selectedRecipeDetail!!.ingredients,
            instructions = selectedRecipeDetail!!.instructions
        )
        return
    }
    MainScaffold(
        selectedTab = selectedTab,
        onTabSelected = onTabSelected,
        showBack = false,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF8F8F8)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Ara",
                            tint = Color(0xFF888888),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Lezzetli bir şey mi arıyorsunuz?",
                            color = Color(0xFF888888),
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFFFE6EA),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = "Kamera",
                                tint = Color(0xFFE11932),
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(24.dp)
                                    .clickable { /* TODO: Camera action */ }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tarifleri Keşfet",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
            DiscoverRecipeStackedCard(onClick = {
                selectedRecipeDetail = RecipeDetail(
                    imageRes = R.drawable.lahmacun,
                    title = "Lahmacun",
                    author = "MealMate Chef",
                    rating = 4.8,
                    ratingsCount = 512,
                    description = "Lahmacun, kıyma, sebze ve otlarla doldurulmuş lezzetli bir Türk gözleme ekmeğidir. Popüler bir sokak yemeği ve Türk mutfağının favorisidir.",
                    prepTime = "30 mins",
                    cookTime = "25 mins",
                    totalTime = "55 mins",
                    servings = "4",
                    calories = "420",
                    fat = "16 g",
                    carbs = "48 g",
                    protein = "20 g",
                    ingredients = listOf(
                        "1 adet soğan",
                        "2 adet domates",
                        "250 gram kıyma",
                        "1 yemek kaşığı biber salçası",
                        "1 tatlı kaşığı tuz",
                        "1 çay kaşığı karabiber",
                        "1 çay kaşığı pul biber",
                        "1/2 çay bardağı sıvı yağ",
                        "1/2 demet maydanoz",
                        "4 adet lahmacun hamuru"
                    ),
                    instructions = listOf(
                        "Lahmacun için önce mutfak robotuna kırmızı biberleri, yeşil biberleri, soğanları ve domatesleri ilave edin.",
                        "Tamamen püre haline gelmeyecek şekilde çekin.",
                        "Maydanozları da ekleyin ve 10 saniye kadar daha çekin.",
                        "Domates salçası, biber salçası, sıvı yağ ve suyu ekleyin. Son olarak kıymayı ekleyerek güzelce yoğurun. Lahmacunun lezzetli olması için harcınızın cıvık bir kıvamda olması gerekir. Yeteri kadar cıvık bir kıvamda değilse, biraz su ekleyebilirsiniz.",
                        "Hazırladığınız harcı vaktiniz varsa bir süre buzdolabında dinlendirin.",
                        "Harcınız dinlenirken, hamurunuzu yapmaya başlayın. Suyu bir kaba dökün, üzerine tuz ve şekeri ilave ederek güzelce karıştırın. Azar azar da un ilave edin. Orta sertliğe ulaşana dek yoğurun.",
                        "Hamuru bir silindir haline getirip eşit parçalara bölün. Aynı büyüklükte lahmacunlar yapabilmek için hamurlarınızın ağırlığı da eşit olmalıdır. Bezeleri bir süre dinlenmeye bırakın.",
                        "Hazırladığınız bezeleri yemek tabağı büyüklüğünde açın. Mümkün olduğu kadar ince açmanız önemlidir. Bu sırada fırınınızı 250 derece fanlı ve alt-üst açık olacak şekilde açın. Güzelce ısıtın.",
                        "Açtığınız hamurların üzerine kıymalı harcı yayın. Fırının tabanına yağlı kağıt ile birlikte yerleştirin ve 7-10 dakika altı kızarana kadar pişirin.",
                        "Sumaklı soğan, maydanoz ve bol limonla servis edin.",
                        "Ev yapımı nefis lahmacun hazır. Afiyet olsun!"
                    )
                )
                showRecipeDetail = true
            })
            CategoryRow()
            PratikTarifler(
                onRecipeClick = { recipe ->
                    selectedRecipeDetail = recipe
                    showRecipeDetail = true
                },
                dolapItems = dolapItems,
                onNavigateToMarket = { onTabSelected(1) }
            )
        }
    }
}

@Composable
fun DiscoverRecipeStackedCard(onClick: () -> Unit) {
    Box(modifier = Modifier.height(210.dp).fillMaxWidth().clickable { onClick() }) {
        // Kart yığını efekti (arka plan kartları)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE6EA)),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 24.dp)
                .fillMaxWidth(0.93f)
                .height(160.dp)
        ) {}
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3F6)),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 12.dp)
                .fillMaxWidth(0.97f)
                .height(170.dp)
        ) {}
        // Kenarlıklı ana kart, gölge yok
        Card(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                // Sol: Bilgi
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp, top = 20.dp, bottom = 20.dp, end = 8.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Türk Yemekleri",
                            color = Color.Gray,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "Lahmacun",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 2.dp, bottom = 4.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // 4.5 kırmızı yıldız
                            repeat(4) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star),
                                    contentDescription = null,
                                    tint = Color(0xFFE11932),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = null,
                                tint = Color(0xFFE11932),
                                modifier = Modifier.size(8.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "512 Rating",
                                color = Color.Gray,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(start = 2.dp)
                            )
                        }
                    }
                    Button(
                        onClick = { /* TODO: Check recipe */ },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11932)),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text(text = "Tarifi İncele", color = Color.White, fontSize = 16.sp)
                    }
                }
                // Sağ: Resim ve yer imi
                Box(modifier = Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.lahmacun),
                        contentDescription = "Lahmacun",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .size(32.dp)
                            .background(Color.White, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bookmark),
                            contentDescription = "Yer İmi",
                            tint = Color(0xFFE11932),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Kategori Seçin",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "See more ",
                color = Color.Gray,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "Daha fazla gör",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CategoryItem(R.drawable.soup, "Çorbalar")
        CategoryItem(R.drawable.breakfast, "Kahvaltı")
        CategoryItem(R.drawable.sea_food, "Deniz")
        CategoryItem(R.drawable.cupcake, "Tatlı")
    }
}

@Composable
fun CategoryItem(iconRes: Int, label: String) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .background(Color(0xFFFFF3F6), shape = RoundedCornerShape(16.dp))
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
    }
}

@Composable
fun PratikTarifler(
    onRecipeClick: (RecipeDetail) -> Unit,
    dolapItems: List<String> = emptyList(),
    onNavigateToMarket: () -> Unit = {}
) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = "Pratik Tarifleri 🍽️",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        if (dolapItems.isEmpty()) {
            // Malzeme yoksa mesaj göster
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3F6)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_market),
                        contentDescription = "Market",
                        tint = Color(0xFFE11932),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Hiç malzemeniz yok",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Kişiselleştirilmiş tarif önerilerini görmek için dolabınıza bazı malzemeler ekleyin",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onNavigateToMarket,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11932)),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_market),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Markete Git",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        } else {
            // Malzeme var ama eşleşen tarif yoksa kontrol et
            val normalizedDolap = dolapItems.map { it.trim().lowercase() }
            val recipes = listOf(
                RecipeDetail(
                    imageRes = R.drawable.mercimek_corbasi,
                    title = "Mercimek Corbasi",
                    author = "Gordon Ramsay",
                    rating = 4.7,
                    ratingsCount = 512,
                    description = "Mercimek çorbası klasik bir Türk mercimek çorbasıdır, doyurucu ve besleyicidir.",
                    prepTime = "20 mins",
                    cookTime = "40 mins",
                    totalTime = "1 hr",
                    servings = "6",
                    calories = "180",
                    fat = "6 g",
                    carbs = "25 g",
                    protein = "8 g",
                    ingredients = listOf(
                        "3 yemek kaşığı sıvı yağ",
                        "1 adet kuru soğan (iri doğranmış)",
                        "1 yemek kaşığı un",
                        "1 adet havuç (iri doğranmış)",
                        "1 adet patates (büyük boy, iri doğranmış)",
                        "1 tatlı kaşığı tuz",
                        "1 çay kaşığı karabiber",
                        "1,5 su bardağı kırmızı ya da sarı mercimek",
                        "6 su bardağı sıcak su (1 adet bulyon ile hazırlanmış)",
                        "---",
                        "Mercimek çorbasının üzeri için:",
                        "3 yemek kaşığı sıvı yağ",
                        "2 yemek kaşığı tereyağı",
                        "1 tatlı kaşığı kırmızı toz biber"
                    ),
                    instructions = listOf(
                        "Mercimek çorbası için önce ayçiçek yağını tencereye alın ve üzerine doğranmış soğanları ilave ederek kavurun.",
                        "Soğanlar kavrulduktan sonra unu ekleyin ve kokusu çıkana kadar kavurun.",
                        "Havuç ve patatesleri de ekleyin ve 2-3 dakika daha kavurun.",
                        "Tuz ve karabiberi ekleyin.",
                        "Mercimekleri ekleyin ve 2-3 dakika daha kavurun.",
                        "Sıcak suyu ekleyin ve kaynamaya bırakın.",
                        "Kaynadıktan sonra kısık ateşte 30 dakika pişirin.",
                        "Çorbayı blenderdan geçirin ve 5 dakika daha pişirin."
                    )
                ),
                RecipeDetail(
                    imageRes = R.drawable.spoonful,
                    title = "Spoonful",
                    author = "Jamie Oliver",
                    rating = 4.7,
                    ratingsCount = 512,
                    description = "Kaşık kaşık yiyebileceğiniz lezzetli bir tatlı!",
                    prepTime = "15 mins",
                    cookTime = "15 mins",
                    totalTime = "30 mins",
                    servings = "4",
                    calories = "250",
                    fat = "10 g",
                    carbs = "35 g",
                    protein = "4 g",
                    ingredients = listOf(
                        "2,5 su bardağı süt",
                        "1/2 su bardağı toz şeker",
                        "1/2 su bardağı un",
                        "2 yemek kaşığı nişasta",
                        "1 adet yumurta",
                        "1 yemek kaşığı tereyağı",
                        "1 paket vanilin",
                        "1 kutu krema",
                        "---",
                        "Spoonful Çikolata Sos İçin:",
                        "160 gram sütlü çikolata",
                        "1 yemek kaşığı sıvı yağ",
                        "---",
                        "Spoonful Tabanı İçin:",
                        "3-4 dilim kek"
                    ),
                    instructions = listOf(
                        "Spoonful için önce bir kabın tabanına kekleri ufalayıp hafif bastırın.",
                        "Diğer tarafta tencereye krema, tereyağı ve vanilya hariç tüm malzemeleri alın. Sürekli karıştırarak kıvam alıncaya kadar pişirin. Kaynamaya başlayınca ocaktan alın.",
                        "Tereyağı ve vanilyayı ekleyin. Ara ara karıştırarak soğumaya bırakın.",
                        "Soğuduktan sonra içerisine kremayı ekleyip çırpın.",
                        "Çikolatayı benmari usulü eritin. İçine sıvı yağ ekleyip karıştırın. Keklerin üzerine hazırlanan kremayı ekleyin.",
                        "En üste çikolata sosu da ekleyip çikolata parçalarıyla süsleyin.",
                        "Spoonful hazır. Afiyet olsun."
                    )
                ),
                RecipeDetail(
                    imageRes = R.drawable.tavuk_sote,
                    title = "Tavuk Sote",
                    author = "Gordon Ramsay",
                    rating = 4.7,
                    ratingsCount = 512,
                    description = "Tavuk Sote, sebzeli bir Türk tavuk sotesidir.",
                    prepTime = "15 mins",
                    cookTime = "25 mins",
                    totalTime = "40 mins",
                    servings = "4",
                    calories = "320",
                    fat = "12 g",
                    carbs = "18 g",
                    protein = "32 g",
                    ingredients = listOf(
                        "2 yemek kaşığı sıvı yağ",
                        "300 gram tavuk göğsü (kuşbaşı doğranmış)",
                        "1 adet soğan",
                        "1 adet yeşil biber",
                        "1/2 adet kırmızı biber",
                        "1 adet doğranmış domates",
                        "1 çay kaşığı tuz",
                        "1 çay kaşığı karabiber",
                        "1 çay kaşığı pul biber",
                        "1 çay kaşığı kuru nane"
                    ),
                    instructions = listOf(
                        "Tavuk sote için bir tavanın içerisine 2 yemek kaşığı sıvı yağı alın ve üzerine 300 gram kuşbaşı doğranmış tavuğu ilave ederek yüksek ateşte soteleyin.",
                        "Pişen tavukların üzerine 1 adet doğranmış soğanı ilave ederek sotelemeye devam edin.",
                        "1 adet doğranmış yeşil biber ve yarım adet doğranmış kapya biberi de ekleyin.",
                        "1 adet doğranmış domatesi de tavanın içerisine aktarın.",
                        "Biraz suyunu çektikten sonra sırasıyla birer çay kaşığı tuz, karabiber, pul biber ve kuru nane ilave ederek güzelce karıştırın.",
                        "İyice pişince tavuk sotenizi ocaktan alın ve servis edin. Tavuk sote hazır. Afiyet olsun!"
                    )
                ),
                RecipeDetail(
                    imageRes = R.drawable.arnavut_cigeri,
                    title = "Arnavut Ciğeri",
                    author = "Jamie Oliver",
                    rating = 4.7,
                    ratingsCount = 512,
                    description = "Arnavut Ciğeri, çıtır çıtır ve lezzetli klasik bir Türk ciğer yemeğidir.",
                    prepTime = "20 mins",
                    cookTime = "15 mins",
                    totalTime = "35 mins",
                    servings = "4",
                    calories = "280",
                    fat = "14 g",
                    carbs = "10 g",
                    protein = "28 g",
                    ingredients = listOf(
                        "600 gram ciğer",
                        "1 çay bardağı un (bulamak için)",
                        "1 çay kaşığı tuz",
                        "1 çay kaşığı karbonat",
                        "1 su bardağı sıvı yağ",
                        "1,5 çay kaşığı toz kırmızı biber",
                        "1 çay kaşığı toz kişniş",
                        "2 adet patates (küp doğranmış)",
                        "2 su bardağı sıvı yağ (patatesi kızartmak için)",
                        "---",
                        "Sumaklı soğan için:",
                        "1 adet kırmızı soğan (piyazlık doğranmış)",
                        "1 çay kaşığı tuz",
                        "1 çay kaşığı sumak",
                        "1/2 demet doğranmış maydanoz"
                    ),
                    instructions = listOf(
                        "Arnavut ciğeri yapımı için üzerinde bulunan zarı ayıkladığınız kuzu ciğerini, kuşbaşı büyüklüğünde kesin. Derin bir süzgece aldığınız doğranmış kuzu ciğerlerini kanının süzülmesi için bekletin.",
                        "Kanını süzdürdüğünüz ciğerleri karbonat ve tuz eklediğiniz una bulayın. Ciğerlerin üzerindeki fazla unu almak için tekrar süzgeçten geçirin.",
                        "Ciğerleri tavadaki ısınmış yağın içerisine koyun. Ciğerler pişmeye başladığında kırmızı toz biber ve toz kişniş ekleyin. Pişen ciğerlerin fazla yağını süzdürerek bir tabağa aktarın.",
                        "Ayrı bir tavada kızdırdığınız yağda küp küp doğradığınız patatesleri kızartın ve ciğerlerin olduğu kaseye ekleyin.",
                        "Piyazlık doğradığınız kırmızı soğanları, 1 çay kaşığı tuz ile ovarak öldürün. Üzerine sumak ve doğranmış maydanozu da ekledikten sonra güzelce karıştırın ve Arnavut ciğerlerinin olduğu kaseye ekleyip diğer malzemelerle birlikte güzelce harmanlayın.",
                        "Arnavut ciğerini servis edeceğiniz tabağa alıp, sevdiklerinizle afiyetle tüketin!"
                    )
                ),
                RecipeDetail(
                    imageRes = R.drawable.lahmacun,
                    title = "Lahmacun",
                    author = "Gordon Ramsay",
                    rating = 4.8,
                    ratingsCount = 512,
                    description = "Lahmacun, kıyma, sebze ve otlarla doldurulmuş lezzetli bir Türk gözleme ekmeğidir. Popüler bir sokak yemeği ve Türk mutfağının favorisidir.",
                    prepTime = "30 mins",
                    cookTime = "25 mins",
                    totalTime = "55 mins",
                    servings = "4",
                    calories = "420",
                    fat = "16 g",
                    carbs = "48 g",
                    protein = "20 g",
                    ingredients = listOf(
                        "1 adet soğan",
                        "2 adet domates",
                        "250 gram kıyma",
                        "1 yemek kaşığı biber salçası",
                        "1 tatlı kaşığı tuz",
                        "1 çay kaşığı karabiber",
                        "1 çay kaşığı pul biber",
                        "1/2 çay bardağı sıvı yağ",
                        "1/2 demet maydanoz",
                        "4 adet lahmacun hamuru"
                    ),
                    instructions = listOf(
                        "Lahmacun için önce mutfak robotuna kırmızı biberleri, yeşil biberleri, soğanları ve domatesleri ilave edin.",
                        "Tamamen püre haline gelmeyecek şekilde çekin.",
                        "Maydanozları da ekleyin ve 10 saniye kadar daha çekin.",
                        "Domates salçası, biber salçası, sıvı yağ ve suyu ekleyin. Son olarak kıymayı ekleyerek güzelce yoğurun. Lahmacunun lezzetli olması için harcınızın cıvık bir kıvamda olması gerekir. Yeteri kadar cıvık bir kıvamda değilse, biraz su ekleyebilirsiniz.",
                        "Hazırladığınız harcı vaktiniz varsa bir süre buzdolabında dinlendirin.",
                        "Harcınız dinlenirken, hamurunuzu yapmaya başlayın. Suyu bir kaba dökün, üzerine tuz ve şekeri ilave ederek güzelce karıştırın. Azar azar da un ilave edin. Orta sertliğe ulaşana dek yoğurun.",
                        "Hamuru bir silindir haline getirip eşit parçalara bölün. Aynı büyüklükte lahmacunlar yapabilmek için hamurlarınızın ağırlığı da eşit olmalıdır. Bezeleri bir süre dinlenmeye bırakın.",
                        "Hazırladığınız bezeleri yemek tabağı büyüklüğünde açın. Mümkün olduğu kadar ince açmanız önemlidir. Bu sırada fırınınızı 250 derece fanlı ve alt-üst açık olacak şekilde açın. Güzelce ısıtın.",
                        "Açtığınız hamurların üzerine kıymalı harcı yayın. Fırının tabanına yağlı kağıt ile birlikte yerleştirin ve 7-10 dakika altı kızarana kadar pişirin.",
                        "Sumaklı soğan, maydanoz ve bol limonla servis edin.",
                        "Ev yapımı nefis lahmacun hazır. Afiyet olsun!"
                    )
                ),
                RecipeDetail(
                    imageRes = R.drawable.domates_corbasi,
                    title = "Domates Çorbası",
                    author = "Gordon Ramsay",
                    rating = 4.7,
                    ratingsCount = 512,
                    description = "Domates Çorbası, her mevsim için mükemmel olan kremalı bir Türk domates çorbasıdır.",
                    prepTime = "15 mins",
                    cookTime = "30 mins",
                    totalTime = "45 mins",
                    servings = "4",
                    calories = "150",
                    fat = "5 g",
                    carbs = "22 g",
                    protein = "4 g",
                    ingredients = listOf(
                        "1 yemek kaşığı tereyağı",
                        "2 çay bardağı sıcak süt",
                        "5 adet büyük boy domates",
                        "2 yemek kaşığı un",
                        "4 su bardağı sıcak su (1 adet bulyon ile hazırlanmış)",
                        "2 çay kaşığı tuz",
                        "---",
                        "Domates çorbasının servisi için:",
                        "1 su bardağı rendelenmiş kaşar peyniri"
                    ),
                    instructions = listOf(
                        "Domates çorbası için önce tereyağını tavada eritin.",
                        "Unu ekleyip kokusu çıkana kadar kısık ateşte kavurun.",
                        "Rendelenmiş domatesleri kavrulmuş un karışımına ekledikten sonra 5 dakika kadar pişirin.",
                        "Üzerine sıcak suyunu ve tuzu ilave edin. Kesilmemesi için küçük bir cezvede ısıttığınız sütü azar azar ekleyip hızlıca karıştırın.",
                        "Çorbayı kaynayana kadar orta ateşte ardından da kısık ateşte 15 dakika kadar pişirin. Daha pürüzsüz bir kıvam alması için blenderdan geçirin. Servis kaselerine aldığınız domates çorbasını, rendelenmiş kaşar peynir ilavesiyle sıcak olarak servis edin. Afiyet olsun!"
                    )
                )
            )
            
            // Akıllı filtreleme mantığı: ana malzeme mevcudiyetine göre tarifleri göster
            val filteredRecipes = if (normalizedDolap.isNotEmpty()) {
                recipes.filter { recipe ->
                    val recipeIngredients = recipe.ingredients
                        .filter { it.isNotBlank() && !it.contains(":") && !it.contains("---") }
                        .map { it.replace(Regex("\\(.*?\\)|[0-9]+|adet|yemek kaşığı|su bardağı|çay bardağı|tatlı kaşığı|gram|büyük boy|küçük boy|iri doğranmış|doğranmış|küp|küp doğranmış|bulamak için|servisi için|üzeri için|için|paket|kutu|dilim|büyük|küçük|orta|boy|\\s+"), "").trim().lowercase() }
                    
                    // Her tarif için ana malzemeleri tanımla (temel olan ana malzemeler)
                    val keyIngredients = when (recipe.title) {
                        "Spoonful" -> listOf("süt", "un", "şeker", "yumurta", "krema", "tereyağı")
                        "Tavuk Sote" -> listOf("tavuk", "soğan", "sıvı yağ", "tuz", "yeşil biber", "domates")
                        "Arnavut Ciğeri" -> listOf("ciğer", "un", "sıvı yağ", "tuz", "patates", "soğan")
                        "Lahmacun" -> listOf("soğan", "domates", "kıyma", "sıvı yağ", "un", "biber salçası")
                        "Mercimek Corbasi" -> listOf("mercimek", "soğan", "sıvı yağ", "tuz", "havuç", "patates")
                        "Domates Çorbası" -> listOf("domates", "un", "süt", "tuz", "tereyağı", "kaşar peyniri")
                        else -> recipeIngredients.take(4) // Varsayılan: ilk 4 malzeme ana malzeme olarak
                    }
                    
                    // TÜM ana malzemelerin mevcut olup olmadığını kontrol et
                    val availableKeyIngredients = keyIngredients.count { ing ->
                        normalizedDolap.any { dolap -> dolap.contains(ing) || ing.contains(dolap) }
                    }
                    
                    // TÜM ana malzemeler mevcutsa tarifi göster
                    availableKeyIngredients == keyIngredients.size
                }
            } else {
                recipes
            }
            
            if (filteredRecipes.isEmpty() && dolapItems.isNotEmpty()) {
                // Malzeme var ama eşleşen tarif yoksa mesaj göster
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3F6)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_market),
                            contentDescription = "Market",
                            tint = Color(0xFFE11932),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Yeterli malzeme yok",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tariflerimizden herhangi birini yapmak için daha fazla malzemeye ihtiyacınız var. Dolabınıza daha fazla ürün ekleyin!",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = onNavigateToMarket,
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11932)),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_market),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Daha Fazla Malzeme Ekle",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            } else {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredRecipes) { recipe ->
                        Box(modifier = Modifier.clickable { onRecipeClick(recipe) }) {
                    PopularTodayCard(
                                imageRes = recipe.imageRes,
                                rating = recipe.rating.toFloat(),
                                chefImageRes = R.drawable.chef,
                                title = recipe.title,
                                chef = recipe.author,
                                time = recipe.prepTime
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PopularTodayCard(
    imageRes: Int,
    rating: Float,
    chefImageRes: Int,
    title: String,
    chef: String,
    time: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(220.dp)
            .height(240.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3F6))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            // Puan üst katmanı
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White.copy(alpha = 0.7f), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    tint = Color(0xFFE8B200),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = rating.toString(),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
            // Şef resmi üst katmanı
            Image(
                painter = painterResource(id = chefImageRes),
                contentDescription = chef,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomEnd)
                        .offset(x = (-8).dp, y = (8).dp)
                    .background(Color.White, shape = CircleShape)
                    .padding(2.dp)
            )
        }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFFF3F6),
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.Black,
                        maxLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
            Text(
                text = "By $chef",
                color = Color.Gray,
                fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
            )
                        Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = time,
                color = Color.Gray,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
            )
                    }
                }
            }
        }
    }
}