package com.nikuts.ratesapp.models.api

import com.squareup.moshi.Json

data class RatesApiModel(
    @field:Json(name = "base") val base: String? = null,
    @field:Json(name = "date") val date: String? = null,
    @field:Json(name = "rates") val rates: Map<String, Float>? = null
)