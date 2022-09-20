package com.app.cocktailapp.ui.detail

import androidx.lifecycle.viewModelScope
import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.domain.usecase.DrinkInfoUseCaseImp
import com.app.cocktailapp.ui.base.BaseViewModel
import com.app.cocktailapp.ui.mapper.DrinkMapperUI
import com.app.cocktailapp.ui.mapper.ErrorMapperUI
import com.app.cocktailapp.ui.model.DrinkInfo
import com.app.cocktailapp.ui.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkInfoViewModel @Inject constructor(
    private val drinkInfoUseCaseImp: DrinkInfoUseCaseImp,
    private val drinkMapperUI: DrinkMapperUI,
    private val errorViewMapper: ErrorMapperUI,
) : BaseViewModel() {

    private val _getDrinkInfoState =
        MutableStateFlow(State<List<DrinkInfo>>(isInitialState = true))
    val getDrinkInfoState: StateFlow<State<List<DrinkInfo>>> = _getDrinkInfoState
    private lateinit var _drinkInfo: List<DrinkInfo>

    fun fetchDrink(id: String) {
        viewModelScope.launch {
            drinkInfoUseCaseImp.getDrinkById(id).collect {
                when (it) {
                    is Resource.Loading -> {
                        _getDrinkInfoState.value = State(isLoading = true)
                    }
                    is Resource.Success -> {
                        _drinkInfo =
                            it.data?.map { drinkData -> drinkMapperUI.mapToOut(drinkData) }
                                ?: listOf()
                        _getDrinkInfoState.value = State(data = _drinkInfo)
                    }
                    is Resource.Error -> {
                        _getDrinkInfoState.value =
                            State(error = errorViewMapper.mapToOut(it.errorEntity))
                    }
                }
            }
        }
    }

    /*fun fetchDrink(id: String) {
        drinkUseCase(id).onEach {
            when (it) {
                is Resource.Loading -> {
                    _getDrinkInfoState.value = State(isLoading = true)
                }
                is Resource.Success -> {
                    _drinkInfo =
                        it.data?.map { drinkData -> drinkMapperUI.mapToOut(drinkData) } ?: listOf()
                    _getDrinkInfoState.value = State(data = _drinkInfo)
                }
                is Resource.Error -> {
                    _getDrinkInfoState.value =
                        State(error = errorViewMapper.mapToOut(it.errorEntity))
                }
            }
        }.launchIn(viewModelScope)
    }*/

}