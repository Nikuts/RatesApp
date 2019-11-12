package com.nikuts.ratesapp

import androidx.lifecycle.ViewModel
import com.nikuts.ratesapp.models.business.CurrencyModel
import com.nikuts.ratesapp.network.RetrofitRatesService

class MainViewModel: ViewModel() {

    private val ratesService = RetrofitRatesService()

    fun loadRates(base: String?) {
        ratesService.getRates(
            base,
            onSuccess = {
                handleRates(it)
            },
            onError = {
                handleError(it)
            }
        )
    }

    private fun handleRates(currency: CurrencyModel?) {

    }

    private fun handleError(message: String?) {

    }
}