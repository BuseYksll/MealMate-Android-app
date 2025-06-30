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

@Composable
fun NavigationScreen(modifier: Modifier = Modifier) {
    var showOnboarding by remember { mutableStateOf(true) }
    var selectedTab by remember { mutableStateOf(0) }
    var dolapItems by remember { mutableStateOf(listOf<String>()) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    if (showOnboarding) {
        OnboardingScreen(
            onFinish = { showOnboarding = false },
            onSkip = { showOnboarding = false }
        )
    } else {
        Box {
            when (selectedTab) {
                0 -> MainScreen(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    modifier = modifier,
                    dolapItems = dolapItems
                )
                1 -> MarketScreen(
                    onConfirm = { items ->
                        dolapItems = dolapItems + items
                        snackbarMessage = "Ürün(ler) dolaba eklendi."
                        showSnackbar = true
                        selectedTab = 2 // Dolap sekmesine geç
                    },
                    onBack = { selectedTab = 0 },
                )
                2 -> DolapScreen(
                    onBack = { selectedTab = 0 },
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    modifier = modifier,
                    dolapItems = dolapItems,
                    onRemoveItem = { item -> dolapItems = dolapItems - item }
                )
            }
            if (showSnackbar) {
                androidx.compose.material3.Snackbar(
                    modifier = Modifier.align(Alignment.Center),
                    containerColor = androidx.compose.ui.graphics.Color(0xFFE11932),
                    contentColor = androidx.compose.ui.graphics.Color.White
                ) {
                    Text(snackbarMessage)
                }
                LaunchedEffect(showSnackbar) {
                    kotlinx.coroutines.delay(2000)
                    showSnackbar = false
                }
            }
        }
    }
} 