package com.nikuts.ratesapp

import com.nikuts.ratesapp.models.business.RatesModel
import com.nikuts.ratesapp.network.Result

interface RatesRepository {
    suspend fun getRates(base: String?): Result<RatesModel>
}