package com.n26.cryptoexchange.data.remote.models.historical

import com.google.gson.annotations.SerializedName

class HistoricalResponse(
    @SerializedName("Response") val response: String,
    @SerializedName("Data") val data: HistoricalData
)

class HistoricalData(
    @SerializedName("Data") val data: List<HistoricalDataEntry>
)

class HistoricalDataEntry(
    val time: Long,
    val close: Double
)
