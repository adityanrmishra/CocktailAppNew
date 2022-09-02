package com.app.cocktailapp.ui.model

data class State<T>(
    val isInitialState: Boolean = false,
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: Error? = null
)
