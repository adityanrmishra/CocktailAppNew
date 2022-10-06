package com.app.cocktailapp.ui.extension

import android.widget.ImageView
import com.app.cocktailapp.R
import com.squareup.picasso.Picasso

fun ImageView.loadImageWithPicassoExt(url: String, isSmallerImage: Boolean) {
    val builder = Picasso.with(context)
        .load(url)
        .error(R.drawable.ic_image_error)
    if (isSmallerImage) {
        builder.resize(256, 256)
        builder.placeholder(R.drawable.splash)
        builder.centerCrop()
    } else {
        builder.fit()
    }
    builder.into(this)
}





