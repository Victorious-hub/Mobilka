package com.example.converter.domain.usecase

import com.example.converter.data.remote.response.CurrencyResponse
import com.example.converter.domain.repository.CurrencyRepository
import com.example.converter.utils.Resource
import javax.inject.Inject

class GetCurrencyRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend fun execute(apiKey: String, currencies: String, baseCurrency: String): Resource<CurrencyResponse> {
        return currencyRepository.getCurrencyRate(apiKey, currencies, baseCurrency)
    }
}