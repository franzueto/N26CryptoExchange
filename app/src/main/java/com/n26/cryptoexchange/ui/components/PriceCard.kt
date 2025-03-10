package com.n26.cryptoexchange.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.n26.cryptoexchange.domain.model.PriceItem
import com.n26.cryptoexchange.ui.formatter.getFormattedDate
import com.n26.cryptoexchange.ui.formatter.getFormattedPrice

@Composable
fun PriceCard(
    item: PriceItem,
    currencySymbol: String = "€",
    alternateLabel: String? = null,
    onClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(item.time)
            }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = alternateLabel ?: item.getFormattedDate(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start
            )
            Text(
                text = item.getFormattedPrice(currencySymbol),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PriceCardPreview() {
    PriceCard(
        PriceItem(
            price = 96274.12,
            time = 1740268800
        ),
        onClick = {}
    )
}
