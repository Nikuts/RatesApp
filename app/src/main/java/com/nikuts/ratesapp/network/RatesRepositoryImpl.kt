package com.nikuts.ratesapp.network

import com.nikuts.ratesapp.RatesRepository
import com.nikuts.ratesapp.models.ApiToBusinessMapper
import com.nikuts.ratesapp.models.business.RatesModel

class RatesRepositoryImpl: RatesRepository {

    private var ratesApi: RatesApi = RetrofitProvider.createRatesApi()

    override suspend fun getRates(base: String?): Result<RatesModel> {
        return safeApiCall { fetchRates(base) }
    }

    private suspend fun fetchRates(base: String?): Result<RatesModel> {
        val response = ratesApi.getRates(base)
        return if (response.isSuccessful) {
            ApiToBusinessMapper.mapRates(response.body())?.let {
                Result.Success(it)
            } ?: Result.Failure(ResponseError.PARSING)
        } else {
            Result.Failure(ResponseError.PARSING)
        }
    }

    private suspend fun <T> safeApiCall(call: suspend () -> Result<T>): Result<T> = try {
        call.invoke()
    } catch (e: Exception) {
        Result.Failure(ResponseError.GENERIC)
    }

}