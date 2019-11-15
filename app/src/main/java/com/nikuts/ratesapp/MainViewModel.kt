package com.nikuts.ratesapp

import androidx.lifecycle.*
import com.nikuts.ratesapp.models.business.RatesModel
import com.nikuts.ratesapp.network.RatesRepositoryImpl
import com.nikuts.ratesapp.network.Result
import com.nikuts.ratesapp.utils.MutableLiveArrayList
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(), CurrencyItem.CurrencyItemEvents {

    val items = MutableLiveArrayList<CurrencyItem>()

    val error = MutableLiveData<String>()

    val scrollEvent = MutableLiveData<Unit>()

    var topItem = CurrencyItem("", 1f, DEFAULT_AMOUNT, true, this)

    private val repository: RatesRepository = RatesRepositoryImpl()

    init {
        loadRates(null)
    }

    fun loadRates(base: String?, scrollToTop: Boolean = false) {
        viewModelScope.launch {
            when (val ratesResult = repository.getRates(base)) {
                is Result.Success -> handleRates(ratesResult.value, scrollToTop)
                is Result.Failure -> handleError(ratesResult.message)
            }
        }
    }

    private fun handleRates(rates: RatesModel, scrollToTop: Boolean) {
        topItem.updateTitle(rates.base)

        updateItems(rates.rates)
        if (scrollToTop) scrollEvent.postValue(null)
    }

    private fun handleError(message: String?) {
        this.error.postValue(message)
    }

    private fun updateItems(rates: Map<String, Float>) {

        val itemsList = if (items.value.isEmpty()) {
            arrayListOf(topItem)
        } else {
            items.value
        }

        rates.forEach { rate ->
            val updateItem = itemsList.find { it.title.get() == rate.key }
            if ( updateItem != null) {
                updateItem.updateRate(rate.value)
                updateItem.updateAmount(topItem.amount.get())
            } else {
                itemsList.add(CurrencyItem(rate.key, rate.value, rate.value * topItem.amount.get(), false, this))
            }
        }

        items.postValue(itemsList)
    }

    override fun onSelected(item: CurrencyItem) {

        items.value.let {
            it.find { it.title == item.title }?.let { item ->
                item.select()
                topItem.deselect()
                topItem = item
                it.remove(item)
                it.add(0, topItem)
            }
            loadRates(topItem.title.get(), true)
        }
    }

    override fun onValueChanged(item: CurrencyItem, value: Float?) {

        if (item != topItem) return

        value?.let { value ->
            items.value.filter { it.title != topItem.title }.forEach {
                it.updateAmount(value)
            }
            items.dispatchChange()
        }
    }

    companion object {
        const val DEFAULT_AMOUNT = 100f
    }
}