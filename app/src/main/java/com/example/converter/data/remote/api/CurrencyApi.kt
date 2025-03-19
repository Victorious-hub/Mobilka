package com.example.converter.data.remote.api

import com.example.converter.data.remote.response.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("v1/latest")
    suspend fun getCurrencyRate(
        @Query("apikey") apiKey: String,
        @Query("currencies") currencies: String,
        @Query("base_currency") baseCurrency: String
    ): CurrencyResponse
}