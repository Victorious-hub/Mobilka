package com.example.converter.data.repository

import android.util.Log
import com.example.converter.data.remote.api.CurrencyApi
import com.example.converter.data.remote.response.CurrencyResponse
import com.example.converter.domain.repository.CurrencyRepository
import com.example.converter.utils.Resource

class CurrencyRepositoryImpl(
    private val api: CurrencyApi,
) : CurrencyRepository {

    override suspend fun getCurrencyRate(
        apiKey: String,
        currencies: String,
        baseCurrency: String
    ): Resource<CurrencyResponse> {
        return try {
            val response = api.getCurrencyRate(apiKey, currencies, baseCurrency)
            Log.d("Currency:", response.toString())
            Resource.Success(response)
        } catch (exception: Exception) {
            exception.localizedMessage?.let { Log.d("Currency:", it) }
            Resource.Error(exception.localizedMessage ?: "An unknown error occurred")
        }
    }
}