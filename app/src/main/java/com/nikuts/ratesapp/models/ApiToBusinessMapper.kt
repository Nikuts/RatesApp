package com.nikuts.ratesapp.models

import com.nikuts.ratesapp.models.api.CurrencyApiModel
import com.nikuts.ratesapp.models.business.CurrencyModel

class ApiToBusinessMapper {

    companion object {

        fun mapCurrency(currencyApiModel: CurrencyApiModel?): CurrencyModel? {
            return if (currencyApiModel?.base != null && currencyApiModel.date != null && currencyApiModel.rates != null) {
                CurrencyModel(currencyApiModel.base, currencyApiModel.date, currencyApiModel.rates)
            } else {
                null
            }
        }
    }
}