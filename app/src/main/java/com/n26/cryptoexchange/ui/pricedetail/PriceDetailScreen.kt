package com.n26.cryptoexchange.ui.pricedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.n26.cryptoexchange.di.ViewModelFactory
import com.n26.cryptoexchange.ui.components.PriceCard
import com.n26.cryptoexchange.ui.formatter.getFormattedDate

@Composable
fun PriceDetailScreen(
    time: Long,
    viewModel: PriceDetailViewModel = viewModel(factory = ViewModelFactory()),
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPriceDetail(time)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            PriceDetailUiState.Loading -> {
                CircularProgressIndicator()
            }

            is PriceDetailUiState.Success -> {
                PriceDetailSuccessScreen(
                    time = time,
                    success = state as PriceDetailUiState.Success,
                    modifier = modifier
                )
            }

            is PriceDetailUiState.Error -> TODO()
        }
    }
}

@Composable
private fun PriceDetailSuccessScreen(
    time: Long,
    success: PriceDetailUiState.Success,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFA726)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "B",
                fontSize = 36.sp,
                color = Color.White,
                fontStyle = FontStyle.Italic
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = time.getFormattedDate(),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        val prices = listOfNotNull(
            success.eurPriceItem?.let { it to ("EUR" to "€") },
            success.usdPriceItem?.let { it to ("USD" to "$") },
            success.gbpPriceItem?.let { it to ("GBP" to "£") },
        )

        prices.forEach { (priceItem, values) ->
            PriceCard(
                priceItem,
                currencySymbol = values.second,
                alternateLabel = values.first,
            ) { }
        }

        if (success.eurPriceItem == null ||
            success.usdPriceItem == null ||
            success.gbpPriceItem == null
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            CircularProgressIndicator()
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewPriceDetailScreen() {
    PriceDetailScreen(time = 1740268800)
}
