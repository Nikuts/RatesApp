package com.nikuts.ratesapp

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableFloat
import java.math.RoundingMode
import java.text.DecimalFormat

open class CurrencyItem(
    val title: String,
    val subtitle: String?,
    val imageUrl: String?,
    rate: Float,
    amount: Float,
    editable: Boolean = false,
    private val events: CurrencyItemEvents? = null
) {

    val rate = ObservableFloat(rate)
    val amount = ObservableFloat(amount)
    val editable = ObservableBoolean(editable)

    fun onItemClicked() {
        events?.onSelected(this)
        select()
    }

    fun onTextChanged(text: CharSequence) {
        val newAmount = text.toString().toFloatOrNull()
        if (newAmount != amount.get()) events?.onValueChanged(this, newAmount)
    }

    fun updateRate(rate: Float) {
        this.rate.set(rate)
    }

    fun updateAmount(amount: Float) {
        this.amount.set(amount * rate.get())
    }

    fun updateEditable(editable: Boolean) {
        this.editable.set(editable)
    }

    fun select() {
        updateEditable(true)
    }

    fun deselect() {
        updateEditable(false)
    }

    fun round2(float: Float): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(float)
    }

    interface CurrencyItemEvents {
        fun onSelected(item: CurrencyItem)
        fun onValueChanged(item: CurrencyItem, value: Float?)
    }
}