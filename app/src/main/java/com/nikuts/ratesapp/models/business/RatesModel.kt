package com.nikuts.ratesapp.models.business

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RatesModel (
    val base: String,
    val date: String,
    val rates: Map<String, Float>
) : Parcelable
