package com.nikuts.ratesapp.utils

import androidx.lifecycle.MutableLiveData

class MutableLiveArrayList<T> : MutableLiveData<ArrayList<T>> {

    constructor() : this(ArrayList())

    constructor(list: List<T>) {
        value = ArrayList(list)
    }

    override fun getValue(): ArrayList<T> {
        return super.getValue() ?: ArrayList()
    }

    override fun setValue(value: ArrayList<T>?) {
        super.setValue(value ?: ArrayList())
    }

    override fun postValue(value: ArrayList<T>?) {
        super.postValue(value ?: ArrayList())
    }

    fun addAndDispatch(t: T) = dispatch { add(t) }

    fun addAndDispatch(index: Int, t: T) = dispatch { add(index, t) }

    fun addAllAndDispatch(list: List<T>) = dispatch { addAll(list) }

    fun removeAndDispatch(t: T) = dispatch { remove(t) }

    fun removeAndDispatch(index: Int) = dispatch { removeAt(index) }

    fun clearAndDispatch() = dispatch { clear() }

    fun dispatchChange() = postValue(value)

    private inline fun dispatch(action: ArrayList<T>.() -> (Unit)) {
        value.let {
            it.action()
            postValue(it)
        }
    }
}