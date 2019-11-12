package com.nikuts.ratesapp.network

import com.nikuts.ratesapp.models.ApiToBusinessMapper
import com.nikuts.ratesapp.models.business.CurrencyModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RetrofitRatesService {

    private var ratesApi: RatesApi = RetrofitProvider.createRatesApi()

    fun getRates(base: String?, onSuccess: (currency: CurrencyModel?) -> Unit, onError: (message: String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ratesApi.getRates(base)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        onSuccess(ApiToBusinessMapper.mapCurrency(response.body()))
                    } else {
                        onError(response.message())
                    }
                } catch (e: Exception) {
                    onError(e.localizedMessage)
                }
            }
        }
    }
}