package com.nikuts.ratesapp.network

import com.nikuts.ratesapp.models.api.RatesApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("/latest")
    suspend fun getRates(@Query("base") base: String?): Response<RatesApiModel>
}