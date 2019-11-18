package com.nikuts.ratesapp

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
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
): BaseObservable() {

    @Bindable
    var rate: Float = rate
        private set

    @Bindable
    var amount: Float = amount
        private set

    @Bindable
    var editable: Boolean = editable
        private set

    fun onItemClicked() {
        events?.onSelected(this)
        select()
    }

    fun onTextChanged(text: CharSequence) {
        val newAmount = text.toString().toFloatOrNull()
        newAmount?.let {
            if (it != amount) {
                amount = it
                events?.onValueChanged(this, it)
            }
        }
    }

    fun updateRate(rate: Float) {
        this.rate = rate
        notifyPropertyChanged(BR.rate)
    }

    fun updateAmount(amount: Float) {
        this.amount = amount * rate
        notifyPropertyChanged(BR.amount)
    }

    fun updateEditable(editable: Boolean) {
        this.editable = editable
        notifyPropertyChanged(BR.editable)
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