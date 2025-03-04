package com.n26.cryptoexchange.ui.pricedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.n26.cryptoexchange.domain.model.PriceItem
import com.n26.cryptoexchange.ui.components.PriceCard

@Composable
fun PriceDetailScreen(id: String, modifier: Modifier = Modifier) {
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
            text = "Jan 25th,\n2025",
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        val currencies = listOf(
            "EUR" to "€ 100.000,00",
            "USD" to "$ 100.000,00",
            "GBP" to "£ 100.000,00"
        )
        currencies.forEach { (currency, amount) ->
            PriceCard(PriceItem(price = amount, date = currency)) { }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewPriceDetailScreen() {
    PriceDetailScreen("test")
}
