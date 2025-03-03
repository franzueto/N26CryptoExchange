package com.n26.cryptoexchange.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.n26.cryptoexchange.R
import com.n26.cryptoexchange.ui.navigation.Routes.PRICES
import com.n26.cryptoexchange.ui.navigation.Routes.PRICE_DETAIL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title = when (currentRoute) {
        PRICES -> stringResource(id = R.string.screen_prices_title)
        PRICE_DETAIL -> stringResource(id = R.string.screen_price_detail_title)
        else -> ""
    }

    if (navController.previousBackStackEntry != null) {
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title
                )
            },
            navigationIcon =  {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    } else {
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title
                )
            }
        )
    }
}
