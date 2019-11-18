package com.nikuts.ratesapp.utils.binding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nikuts.ratesapp.R
import kotlin.random.Random

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(view.context)
            .load(it)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(ColorDrawable(Color.LTGRAY))
            .into(view)
    } ?: run {
        val color = Color.rgb(
            Random.nextInt(0, 255),
            Random.nextInt(0, 255),
            Random.nextInt(0, 255)
        )

        Glide.with(view.context)
            .load(ColorDrawable(color))
            .apply(RequestOptions.circleCropTransform())
            .into(view)
    }
}