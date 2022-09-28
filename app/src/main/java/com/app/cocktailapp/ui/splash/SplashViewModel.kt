package com.app.cocktailapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.cocktailapp.ui.base.BaseViewModel
import com.app.cocktailapp.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() :
    BaseViewModel() {

    private val _isOk = MutableLiveData<Resource<Boolean>>()
    val isOk: LiveData<Resource<Boolean>> = _isOk

    init {
        load()
    }

    fun load(timeMillis: Long = SPLASH_TIME) {
        _isOk.value = Resource.Loading()
        viewModelScope.launch {
            delay(timeMillis = timeMillis)
            _isOk.postValue(Resource.Success(true))
        }
    }

    companion object {
        const val SPLASH_TIME = 2000L
    }
}