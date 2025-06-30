package com.example.mealmate.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ANA UYGULAMA NAVİGASYON EKRANI
 * 
 * Bu ekran uygulamanın ana kontrol merkezidir. Kullanıcının hangi ekranda olduğunu
 * yönetir ve ekranlar arası geçişleri kontrol eder.
 * 
 * UYGULAMA YAPISI:
 * - selectedTab: Hangi sekmenin aktif olduğunu tutar
 *   * 0: Ana Ekran (MainScreen) - Tarifler ve kategoriler
 *   * 1: Market Ekranı (MarketScreen) - Malzeme seçimi
 *   * 2: Dolap Ekranı (DolapScreen) - Kullanıcının malzemeleri
 * 
 * VERİ AKIŞI:
 * 1. Kullanıcı MarketScreen'de malzeme seçer
 * 2. "Seçimi Onayla" butonuna basar
 * 3. dolapItems listesine seçilen malzemeler eklenir
 * 4. Kullanıcı otomatik olarak Dolap ekranına yönlendirilir
 * 5. MainScreen'de PratikTarifler bu malzemelere göre tarif önerir
 */
@Composable
fun NavigationScreen(modifier: Modifier = Modifier) {
    // DURUM YÖNETİMİ
    var showOnboarding by remember { mutableStateOf(true) } // İlk açılış ekranını göster
    var selectedTab by remember { mutableStateOf(0) } // Aktif sekme (0: Ana, 1: Market, 2: Dolap)
    var dolapItems by remember { mutableStateOf(listOf<String>()) } // Kullanıcının dolabındaki malzemeler
    var showSnackbar by remember { mutableStateOf(false) } // Bilgi mesajını göster/gizle
    var snackbarMessage by remember { mutableStateOf("") } // Bilgi mesajı metni

    // İLK AÇILIŞ EKRANI KONTROLÜ
    if (showOnboarding) {
        OnboardingScreen(
            onFinish = { showOnboarding = false }, // Onboarding'i bitir
            onSkip = { showOnboarding = false } // Onboarding'i atla
        )
    } else {
        // ANA UYGULAMA İÇERİĞİ
        Box {
            // SEKMELER ARASI GEÇİŞ YÖNETİMİ
            when (selectedTab) {
                0 -> MainScreen(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }, // Sekme değiştirme
                    modifier = modifier,
                    dolapItems = dolapItems // Dolap malzemelerini MainScreen'e gönder
                )
                1 -> MarketScreen(
                    onConfirm = { items ->
                        // MALZEME EKLEME İŞLEMİ
                        dolapItems = dolapItems + items // Seçilen malzemeleri dolaba ekle
                        snackbarMessage = "Ürün(ler) dolaba eklendi." // Başarı mesajı
                        showSnackbar = true // Mesajı göster
                        selectedTab = 2 // Dolap sekmesine geç - kullanıcı eklenen malzemeleri görebilsin
                    },
                    onBack = { selectedTab = 0 }, // Ana ekrana geri dön
                )
                2 -> DolapScreen(
                    onBack = { selectedTab = 0 }, // Ana ekrana geri dön
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    modifier = modifier,
                    dolapItems = dolapItems, // Dolap malzemelerini göster
                    onRemoveItem = { item -> dolapItems = dolapItems - item } // Malzeme silme
                )
            }
            
            // BİLGİ MESAJI (SNACKBAR) GÖSTERİMİ
            if (showSnackbar) {
                androidx.compose.material3.Snackbar(
                    modifier = Modifier.align(Alignment.Center),
                    containerColor = androidx.compose.ui.graphics.Color(0xFFE11932), // Uygulama teması: kırmızı
                    contentColor = androidx.compose.ui.graphics.Color.White // Beyaz metin
                ) {
                    Text(snackbarMessage)
                }
                // OTOMATİK MESAJ KAPATMA
                LaunchedEffect(showSnackbar) {
                    kotlinx.coroutines.delay(2000) // 2 saniye sonra mesajı kapat
                    showSnackbar = false
                }
            }
        }
    }
} 