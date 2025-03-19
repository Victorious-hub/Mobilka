package com.example.converter.di

import android.provider.SyncStateContract
import com.example.converter.data.remote.api.CurrencyApi
import com.example.converter.data.repository.CurrencyRepositoryImpl
import com.example.converter.domain.repository.CurrencyRepository
import com.example.converter.utils.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): CurrencyApi {
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(currencyApi: CurrencyApi): CurrencyRepository =
        CurrencyRepositoryImpl(currencyApi)
}