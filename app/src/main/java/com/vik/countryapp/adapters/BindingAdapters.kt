package com.vik.countryapp.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vik.countryapp.models.Country
import com.vik.countryapp.models.Currency
import java.lang.StringBuilder
import android.R
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import androidx.core.view.ViewCompat.animate
import java.io.InputStream


@BindingAdapter("bind:imageUrl")
fun ImageView.loadImage(url: String?) {

    val requestOptions = RequestOptions()
        .error(ColorDrawable(Color.WHITE))
        .placeholder(ColorDrawable(Color.WHITE))
        .diskCacheStrategy(DiskCacheStrategy.ALL)


    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .into(this)
}

@BindingAdapter("bind:currency")
fun TextView.setCurrency(country: Country) {
    val currencies = country.currencies ?: arrayListOf()

    if (currencies.isEmpty()) {
        this.text = "-"
    } else if (currencies.size == 1) {
        this.text = currencies.firstOrNull()?.name ?: "-"
    } else {
        val builder = StringBuilder(currencies.firstOrNull()?.name ?: "-")
        for (i in 1..currencies.lastIndex) {
            currencies.getOrNull(i)?.let { builder.append(", ${it.name}") }
        }
    }
}