package com.app.cocktailapp.ui.extension

import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.cocktailapp.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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


