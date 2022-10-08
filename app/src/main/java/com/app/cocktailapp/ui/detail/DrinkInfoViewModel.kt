package com.app.cocktailapp.ui.detail

import androidx.lifecycle.viewModelScope
import com.app.cocktailapp.domain.model.Resource
import com.app.cocktailapp.domain.usecase.DrinkInfoUseCaseImp
import com.app.cocktailapp.ui.base.BaseViewModel
import com.app.cocktailapp.ui.mapper.DrinkMapperUI
import com.app.cocktailapp.ui.mapper.ErrorMapperUI
import com.app.cocktailapp.ui.model.DrinkInfo
import com.app.cocktailapp.ui.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkInfoViewModel @Inject constructor(
    private val drinkInfoUseCaseImp: DrinkInfoUseCaseImp,
    private val drinkMapperUI: DrinkMapperUI,
    private val errorViewMapper: ErrorMapperUI,
) : BaseViewModel() {

    private val _getDrinkInfoUiState =
        MutableStateFlow<UiState<DrinkInfo>>(UiState.InitialState())
    val getDrinkInfoUiState: StateFlow<UiState<DrinkInfo>> = _getDrinkInfoUiState

    fun fetchDrink(id: String) {
        _getDrinkInfoUiState.update { (UiState.ShowLoading()) }
        viewModelScope.launch {
            drinkInfoUseCaseImp.getDrinkById(id).collect {
                when (it) {
                    is Resource.Success -> {
                        val drinkInfo =
                            it.data?.map { drinkData -> drinkMapperUI.mapToOut(drinkData) }
                                ?: listOf()

                        if (drinkInfo.isEmpty()) {
                            _getDrinkInfoUiState.update { UiState.ShowEmptyData() }
                        } else {
                            _getDrinkInfoUiState.update { UiState.ShowData(drinkInfo.first()) }
                        }
                    }
                    is Resource.Error -> {
                        val error = it.message
                        _getDrinkInfoUiState.update {
                            UiState.ShowError(
                                errorViewMapper.mapToOut(
                                    error.toString()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}