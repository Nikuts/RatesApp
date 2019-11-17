package com.nikuts.ratesapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikuts.ratesapp.models.business.RatesModel
import com.nikuts.ratesapp.network.RatesRepositoryImpl
import com.nikuts.ratesapp.network.ResponseError
import com.nikuts.ratesapp.network.Result
import com.nikuts.ratesapp.utils.MutableLiveArrayList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(), CurrencyItem.CurrencyItemEvents {

    val items = MutableLiveArrayList<CurrencyItem>()

    val error = MutableLiveData<ResponseError>()

    val scrollEvent = MutableLiveData<Unit>()

    val loading = MutableLiveData<Boolean>(false)

    private val repository: RatesRepository = RatesRepositoryImpl()

    private lateinit var topItem: CurrencyItem

    private var base: String? = null

    private var initialLoading = true

    private var loadRatesJob: Job? = null

    init {
        loadRatesPeriodically(DEFAULT_DELAY)
    }

    private suspend fun loadRates(scrollToTop: Boolean) {
        if (initialLoading) loading.postValue(true)
        when (val ratesResult = repository.getRates(base)) {
            is Result.Success -> handleRates(ratesResult.value, scrollToTop)
            is Result.Failure -> handleError(ratesResult.error)
        }
        loading.postValue(false)
    }

    private fun loadRatesPeriodically(periodMs: Long) {
        viewModelScope.launch {
            while(true) {
                delay(periodMs)
                loadRatesJob.let {
                    if (it == null || !it.isActive) loadRates(false)
                }
            }
        }
    }

     private fun loadRatesOnce(scrollToTop: Boolean = false) {
        loadRatesJob = viewModelScope.launch {
            loadRates(scrollToTop)
        }
     }

    private fun handleRates(rates: RatesModel, scrollToTop: Boolean) {
        if (initialLoading) {
            base = rates.base
            topItem = CurrencyItem(
                rates.base,
                MOCKED_SUBTITLE,
                null,
                1f,
                DEFAULT_AMOUNT,
                false,
                this
            )
            initialLoading = false
        }

        updateItems(rates.rates)
        if (scrollToTop) scrollEvent.postValue(null)
    }

    private fun handleError(error: ResponseError) {
        this.error.postValue(error)
    }

    private fun updateItems(rates: Map<String, Float>) {

        val itemsList = if (items.value.isEmpty()) {
            arrayListOf(topItem)
        } else {
            items.value
        }

        rates.forEach { rate ->
            val updateItem = itemsList.find { it.title == rate.key }
            if ( updateItem != null) {
                updateItem.updateRate(rate.value)
                updateItem.updateAmount(topItem.amount.get())
            } else {
                itemsList.add(CurrencyItem(
                    rate.key,
                    MOCKED_SUBTITLE,
                    null,
                    rate.value,
                    rate.value * topItem.amount.get(),
                    false,
                    this
                ))
            }
        }

        items.postValue(itemsList)
    }

    override fun onSelected(item: CurrencyItem) {

        items.value.let {
            it.find { it.title == item.title }?.let { item ->
                topItem.deselect()
                topItem = item
                base = item.title
                it.remove(item)
                it.add(0, topItem)
            }
            loadRatesOnce(true)
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
        const val DEFAULT_DELAY = 1000L

        const val MOCKED_SUBTITLE = "Subtitle"
    }
}