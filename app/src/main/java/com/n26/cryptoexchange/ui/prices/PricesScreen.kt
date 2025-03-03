package com.n26.cryptoexchange.ui.prices

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.n26.cryptoexchange.di.ViewModelFactory
import com.n26.cryptoexchange.domain.model.PriceItem
import com.n26.cryptoexchange.ui.components.PriceCard

@Composable
fun PricesScreen(
    onItemClick: () -> Unit,
    viewModel: PricesViewModel = viewModel(factory = ViewModelFactory())
) {
    val items by viewModel.priceItems.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPrices()
    }

    PricesView(
        items = items,
        onClick = onItemClick
    )
}

@Composable
fun PricesView(items: List<PriceItem>, onClick: () -> Unit) {
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
            price = "$${(index + 1) * 10}.00",
            date = "2025-02-${index + 1}"
        )
    }
    Column {
        PricesView(items = items, onClick = {})
    }
}
