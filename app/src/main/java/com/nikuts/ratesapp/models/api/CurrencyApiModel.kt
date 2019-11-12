package com.nikuts.ratesapp.models.api

import com.squareup.moshi.Json

data class CurrencyApiModel(
    @field:Json(name = "base") val base: String? = null,
    @field:Json(name = "date") val date: String? = null,
    @field:Json(name = "rates") val rates: Map<String, Double>? = null
)