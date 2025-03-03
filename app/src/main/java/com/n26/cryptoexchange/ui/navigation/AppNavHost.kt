package com.n26.cryptoexchange.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.n26.cryptoexchange.ui.navigation.Routes.PRICES
import com.n26.cryptoexchange.ui.navigation.Routes.PRICE_DETAIL
import com.n26.cryptoexchange.ui.pricedetail.PriceDetailScreen
import com.n26.cryptoexchange.ui.prices.PricesScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PRICES
    ) {
        composable(route = PRICES) {
            PricesScreen(
                onItemClick = {
                    navController.navigate(PRICE_DETAIL)
                }
            )
        }
        composable(route = PRICE_DETAIL) {
            PriceDetailScreen("")
        }
    }
}
