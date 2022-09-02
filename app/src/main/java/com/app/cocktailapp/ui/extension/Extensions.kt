package com.app.cocktailapp.ui.extension

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.cocktailapp.R

fun View.setOnSafeClickListener(
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(SafeClickListener { v ->
        onSafeClick(v)
    })
}

fun View.setOnSafeClickListener(
    interval: Int,
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(SafeClickListener(interval) { v ->
        onSafeClick(v)
    })
}

fun SwipeRefreshLayout.applyTheme() {
    setColorSchemeResources(
        R.color.planePrimaryTextColor,
        R.color.colorCardBg1,
        R.color.colorCardBg2
    )
    setProgressBackgroundColorSchemeResource(R.color.background)
}