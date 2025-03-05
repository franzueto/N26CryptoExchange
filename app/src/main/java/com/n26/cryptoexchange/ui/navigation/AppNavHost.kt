package com.n26.cryptoexchange.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.LongType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                onItemClick = { time ->
                    navController.navigate("$PRICE_DETAIL/$time")
                }
            )
        }
        composable(
            route = "$PRICE_DETAIL/{time}",
            arguments = listOf(
                navArgument("time") {
                    type = LongType
                }
            )
        ) { backStackEntry ->
            val time = backStackEntry.arguments?.getLong("time")
            PriceDetailScreen(time!!)
        }
    }
}
