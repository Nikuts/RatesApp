package com.nikuts.ratesapp.utils.binding

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean

@BindingAdapter("requestFocus")
fun requestFocus(view: EditText, requestFocus: Boolean) {
    if (requestFocus) {
        view.requestFocus()
        view.setSelection(view.text.length)
        view.imeOptions = EditorInfo.IME_ACTION_DONE

        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
            showSoftInput(view, InputMethod.SHOW_EXPLICIT)
        }
    }
}