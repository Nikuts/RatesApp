package com.nikuts.ratesapp.models

import com.nikuts.ratesapp.models.api.RatesApiModel
import com.nikuts.ratesapp.models.business.RatesModel

class ApiToBusinessMapper {

    companion object {

        fun mapRates(ratesApiModel: RatesApiModel?): RatesModel? {
            return if (ratesApiModel?.base != null && ratesApiModel.date != null && ratesApiModel.rates != null) {
                RatesModel(ratesApiModel.base, ratesApiModel.date, ratesApiModel.rates)
            } else {
                null
            }
        }
    }
}