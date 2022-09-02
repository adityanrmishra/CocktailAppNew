package com.app.cocktailapp.ui.detail

import androidx.lifecycle.viewModelScope
import com.app.cocktailapp.core.Resource
import com.app.cocktailapp.domain.usecase.DrinkUseCase
import com.app.cocktailapp.ui.base.BaseViewModel
import com.app.cocktailapp.ui.mapper.DrinkMapperUI
import com.app.cocktailapp.ui.mapper.ErrorViewMapperUI
import com.app.cocktailapp.ui.model.Drink
import com.app.cocktailapp.ui.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DrinkInfoViewModel @Inject constructor(
    private val errorViewMapper: ErrorViewMapperUI,
    private val drinkUseCase: DrinkUseCase,
    private val drinkMapperUI: DrinkMapperUI
) : BaseViewModel() {

    private val _getDrinkState =
        MutableStateFlow(State<List<Drink>>(isInitialState = true))
    val getDrinkState: StateFlow<State<List<Drink>>> = _getDrinkState
    private lateinit var _drink: List<Drink>


    fun fetchDrinks(id: String) {
        drinkUseCase(id).onEach {
            when (it) {
                is Resource.Loading -> {
                    _getDrinkState.value = State(isLoading = true)
                }
                is Resource.Success -> {
                    _drink =
                        it.data?.map { drinkData -> drinkMapperUI.mapToOut(drinkData) } ?: listOf()
                    _getDrinkState.value = State(data = _drink)
                }
                is Resource.Error -> {
                    _getDrinkState.value =
                        State(error = errorViewMapper.mapToOut(it.errorEntity))
                }
            }
        }.launchIn(viewModelScope)
    }

}