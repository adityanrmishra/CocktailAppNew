package com.app.cocktailapp.ui.model

data class Error(
    val message: String,
    @ErrorType val type: Int,
    val data: Any? = null
)