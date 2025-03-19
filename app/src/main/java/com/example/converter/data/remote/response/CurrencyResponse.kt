package com.example.converter.data.remote.response

import com.google.gson.annotations.SerializedName


data class CurrencyResponse(
    @SerializedName("data")
    val data: CurrencyData
)

data class CurrencyData(
    val USD: Double,
    val BGN: Double,
    val BRL: Double,
    val CAD: Double,
    val CZK: Double,
    val DKK: Double,
    val EUR: Double,
    val RUB: Double
)

