package com.nikuts.ratesapp.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitProvider {

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org/"

        fun createRatesApi(): RatesApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(RatesApi::class.java)
        }
    }
}