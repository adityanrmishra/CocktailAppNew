package com.app.cocktailapp.ui.home

import androidx.lifecycle.viewModelScope
import com.app.cocktailapp.ui.base.BaseViewModel
import com.app.cocktailapp.domain.model.Resource
import com.app.cocktailapp.domain.usecase.DrinksUseCase
import com.app.cocktailapp.domain.usecase.FilterUseCase
import com.app.cocktailapp.ui.mapper.DrinksMapperUI
import com.app.cocktailapp.ui.mapper.ErrorMapperUI
import com.app.cocktailapp.ui.mapper.FilterMapperUI
import com.app.cocktailapp.ui.model.Drink
import com.app.cocktailapp.ui.model.Filter
import com.app.cocktailapp.ui.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinksViewModel @Inject constructor(
    private val filterUseCase: FilterUseCase,
    private val filterMapperUI: FilterMapperUI,
    private val drinksUseCase: DrinksUseCase,
    private val drinksMapperUI: DrinksMapperUI,
    private val errorViewMapper: ErrorMapperUI,
) :
    BaseViewModel() {

    var defaultCategory = "Ordinary Drink"

    private val _getFilterUiState =
        MutableStateFlow<UiState<List<Filter>>>(UiState.InitialState())
    val getFilterUiState: StateFlow<UiState<List<Filter>>> = _getFilterUiState

    private val _getDrinkUiState =
        MutableStateFlow<UiState<List<Drink>>>(UiState.InitialState())
    val getDrinkUiState: StateFlow<UiState<List<Drink>>> = _getDrinkUiState

    fun fetchDrinkFilter() {
        _getFilterUiState.value = UiState.ShowLoading()

        viewModelScope.launch {
            filterUseCase.getFilters().collect {
                when (it) {
                    is Resource.Success -> {
                        val filterList =
                            it.data?.map { filterData -> filterMapperUI.mapToOut(filterData) }
                                ?: listOf()

                        if (filterList.isEmpty()) {
                            _getFilterUiState.value = UiState.ShowEmptyData()
                        } else {
                            _getFilterUiState.value = UiState.ShowData(data = filterList)
                        }
                    }
                    is Resource.Error -> {
                        val error = it.message
                        _getFilterUiState.value = UiState.ShowError(
                            errorViewMapper.mapToOut(
                                error.toString()
                            )
                        )
                    }
                }
            }
        }
    }

    fun fetchDrinks(category: String) {
        _getDrinkUiState.update { UiState.ShowLoading() }
        viewModelScope.launch {
            drinksUseCase.fetchDrinksByCategory(category).collect {
                when (it) {
                    is Resource.Success -> {
                        val drinkList =
                            it.data?.map { drinksData -> drinksMapperUI.mapToOut(drinksData) }
                                ?: listOf()

                        if (drinkList.isEmpty()) {
                            _getDrinkUiState.update { UiState.ShowEmptyData() }
                        } else {
                            _getDrinkUiState.update { UiState.ShowData(data = drinkList) }
                        }
                    }
                    is Resource.Error -> {
                        val error = it.message
                        _getDrinkUiState.update {
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

    fun setDrinkCategory(category: String) {
        if (!isCheckedChip(category)) {
            defaultCategory = category
        }
    }

    fun isCheckedChip(category: String): Boolean {
        return category == defaultCategory
    }

}