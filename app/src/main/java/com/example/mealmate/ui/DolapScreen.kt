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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.border
import androidx.compose.foundation.lazy.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

/**
 * DOLAP EKRANI (DOLAP SCREEN)
 * 
 * Bu ekran kullanıcının dolabındaki mevcut malzemeleri gösterdiği ve yönettiği
 * ana dolap arayüzüdür.
 * 
 * ANA ÖZELLİKLER:
 * - Dolap malzemelerini listeleme: Kullanıcının sahip olduğu tüm malzemeler
 * - Malzeme silme: Dolaptan malzeme çıkarma
 * - Boş dolap durumu: Malzeme yoksa uygun mesaj gösterme
 * - Market yönlendirmesi: Malzeme eklemek için markete gitme
 * 
 * VERİ AKIŞI:
 * 1. NavigationScreen'den dolapItems listesi alınır
 * 2. Malzemeler ekranda listelenir
 * 3. Kullanıcı malzeme silebilir (onRemoveItem callback)
 * 4. Boş dolap durumunda markete yönlendirme seçeneği
 * 
 * DOLAP YÖNETİMİ:
 * - Malzemeler NavigationScreen'de merkezi olarak yönetilir
 * - MarketScreen'den eklenen malzemeler burada görünür
 * - Silinen malzemeler NavigationScreen'deki listeyi günceller
 * - Bu sayede tüm ekranlar arasında tutarlılık sağlanır
 */
@Composable
fun DolapScreen(
    onBack: () -> Unit, // Ana ekrana geri dönmek için callback
    selectedTab: Int, // Aktif sekme bilgisi
    onTabSelected: (Int) -> Unit, // Sekme değiştirme callback'i
    modifier: Modifier = Modifier,
    dolapItems: List<String> = emptyList(), // Kullanıcının dolabındaki malzemeler
    onRemoveItem: (String) -> Unit = {} // Malzeme silme callback'i
) {
    // Geri tuşu kontrolü
    BackHandler {
        onBack()
    }
    
    // Sadece alt navigasyon çubuğu için MainScaffold kullan, selamlama veya eylemler için değil
    MainScaffold(
        selectedTab = selectedTab,
        onTabSelected = onTabSelected,
        showBack = false, // Dolap ekranında geri butonu gösterme
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Özel başlık: geri butonu + Dolap başlığı
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Geri butonu
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
                // Ekran başlığı
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
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // DOLAP BOŞ KONTROLÜ
                if (dolapItems.isEmpty()) {
                    // Boş dolap durumu - kullanıcıyı markete yönlendir
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3F6)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Market ikonu
                            Icon(
                                painter = painterResource(id = R.drawable.ic_market),
                                contentDescription = "Market",
                                tint = Color(0xFFE11932),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            // Başlık
                            Text(
                                text = "Dolabınız boş",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // Açıklama
                            Text(
                                text = "Dolabınıza malzeme eklemek için markete gidin ve istediğiniz malzemeleri seçin",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            // Market butonu
                            Button(
                                onClick = { onTabSelected(1) }, // Market sekmesine geç
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
                    // DOLAP İÇERİĞİ GÖSTERİMİ
                    // Başlık
                    Text(
                        text = "Dolabınızdaki Malzemeler",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    
                    // Malzeme listesi
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(dolapItems) { item ->
                            // MALZEME KARTI
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Malzeme ikonu (basit daire)
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(Color(0xFFFFF3F6), shape = CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_list),
                                            contentDescription = null,
                                            tint = Color(0xFFE11932),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.width(12.dp))
                                    
                                    // Malzeme adı
                                    Text(
                                        text = item.replaceFirstChar { it.uppercase() }, // İlk harfi büyük yap
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        modifier = Modifier.weight(1f)
                                    )
                                    
                                    // Silme butonu
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_pen),
                                        contentDescription = "Sil",
                                        tint = Color(0xFFE11932),
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable { onRemoveItem(item) } // Malzemeyi sil
                                    )
                                }
                            }
                        }
                    }
                    
                    // Alt bilgi kartı
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "💡 İpucu",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ana ekranda bu malzemelerle yapabileceğiniz tarifler gösterilecektir.",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}