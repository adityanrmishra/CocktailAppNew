package com.app.cocktailapp.ui.model

sealed class UiState<T> {
    class InitialState<T> : UiState<T>()
    class ShowLoading<T> : UiState<T>()
    class ShowEmptyData<T> : UiState<T>()
    class ShowData<T>(val data: T) : UiState<T>()
    class ShowError<T>(val error: Error) : UiState<T>()
}
