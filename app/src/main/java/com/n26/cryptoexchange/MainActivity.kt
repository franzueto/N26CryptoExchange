package com.n26.cryptoexchange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.n26.cryptoexchange.ui.navigation.AppNavHost
import com.n26.cryptoexchange.ui.navigation.TopAppBarNavigation
import com.n26.cryptoexchange.ui.theme.CryptoExchangeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoExchangeTheme {
                CryptoExchangeApp()
            }
        }
    }
}

@Composable
fun CryptoExchangeApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarNavigation(navController)
        }
    ) { innerPadding ->
        AppNavHost(navController, Modifier.padding(innerPadding))
    }
}
