package com.app.cocktailapp.ui.extension

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.cocktailapp.R
import com.squareup.picasso.Picasso

@BindingAdapter("show")
fun setVisibility(v: View, show: Boolean) {
    v.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl", "isSmallerImage", requireAll = false)
fun setImageUrl(imageView: ImageView, url: String?, isSmallerImage: Boolean) {
    val builder = Picasso.with(imageView.context)
        .load(url)
        .error(R.drawable.ic_image_error)
    if (isSmallerImage) {
        builder.resize(256, 256)
        builder.placeholder(R.drawable.splash)
        builder.centerCrop()
    } else {
        builder.fit()
    }
    builder.into(imageView)
}