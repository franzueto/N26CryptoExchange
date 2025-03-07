package com.n26.cryptoexchange.ui.prices

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.n26.cryptoexchange.di.ViewModelFactory
import com.n26.cryptoexchange.domain.model.PriceItem
import com.n26.cryptoexchange.ui.components.PriceCard

@Composable
fun PricesScreen(
    onItemClick: (time: Long) -> Unit,
    viewModel: PricesViewModel = viewModel(factory = ViewModelFactory())
) {
    val items by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPrices()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (items) {
            PricesUiState.Loading -> CircularProgressIndicator()
            is PricesUiState.Success -> {
                PricesView(
                    items = (items as PricesUiState.Success).items,
                    onClick = onItemClick
                )
            }
            is PricesUiState.Error -> TODO()
        }
    }
}

@Composable
fun PricesView(items: List<PriceItem>, onClick: (Long) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(items) { item ->
            PriceCard(
                item = item,
                onClick = onClick
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PricesViewPreview() {
    val items = List(15) { index ->
        PriceItem(
            price = 96274.12,
            time = 1740268800
        )
    }
    Column {
        PricesView(items = items, onClick = {})
    }
}
