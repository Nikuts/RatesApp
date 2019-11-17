package com.nikuts.ratesapp.utils.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nikuts.ratesapp.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(view.context)
            .load(it)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.flag_placeholder)
            .into(view)
    } ?: Glide.with(view.context)
        .load(R.drawable.flag_placeholder)
        .apply(RequestOptions.circleCropTransform())
        .into(view)
}