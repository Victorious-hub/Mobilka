package com.example.converter.domain.repository

import com.example.converter.data.remote.response.CurrencyResponse
import com.example.converter.utils.Resource

interface CurrencyRepository {
    suspend fun getCurrencyRate(apiKey: String, currencies: String, baseCurrency: String): Resource<CurrencyResponse>
}
