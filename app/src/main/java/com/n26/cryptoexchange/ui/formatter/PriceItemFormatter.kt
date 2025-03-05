package com.n26.cryptoexchange.ui.formatter

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import com.n26.cryptoexchange.domain.model.PriceItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.text.replace
import kotlin.text.trim

internal fun Long.getFormattedDate(): String {
    val instant = Instant.ofEpochSecond(this)

    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())

    return localDateTime.format(formatter)
}

internal fun PriceItem.getFormattedDate(): String {
    return time.getFormattedDate()
}

internal fun PriceItem.getFormattedPrice(
    currencySymbol: String,
    locale: Locale = Locale.getDefault()
): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)

    val decimalFormatSymbols = (currencyFormatter as DecimalFormat).decimalFormatSymbols
    decimalFormatSymbols.currencySymbol = ""

    currencyFormatter.decimalFormatSymbols = decimalFormatSymbols

    val newPattern = currencyFormatter.toPattern().replace("\u00A4", "").trim()
    val newFormat = DecimalFormat(newPattern, decimalFormatSymbols)

    return "$currencySymbol ${newFormat.format(price)}"
}
