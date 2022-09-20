package com.app.cocktailapp.ui.home

import androidx.lifecycle.viewModelScope
import com.app.cocktailapp.ui.base.BaseViewModel
import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.domain.usecase.DrinksUseCaseImp
import com.app.cocktailapp.domain.usecase.FilterUseCaseImp
import com.app.cocktailapp.ui.mapper.DrinksMapperUI
import com.app.cocktailapp.ui.mapper.ErrorMapperUI
import com.app.cocktailapp.ui.mapper.FilterMapperUI
import com.app.cocktailapp.ui.model.Drink
import com.app.cocktailapp.ui.model.Filter
import com.app.cocktailapp.ui.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinksViewModel @Inject constructor(
    private val filterUseCaseImp: FilterUseCaseImp,
    private val filterMapperUI: FilterMapperUI,
    private val drinksUseCaseImp: DrinksUseCaseImp,
    private val drinksMapperUI: DrinksMapperUI,
    private val errorViewMapper: ErrorMapperUI,
) :
    BaseViewModel() {

    var defaultCategory = "Ordinary Drink"

    private val _getFilterState =
        MutableStateFlow(State<List<Filter>>(isInitialState = true))
    val getFilterState: StateFlow<State<List<Filter>>> = _getFilterState
    private lateinit var _filterList: List<Filter>

    private val _getDrinkState =
        MutableStateFlow(State<List<Drink>>(isInitialState = true))
    val getDrinkState: StateFlow<State<List<Drink>>> = _getDrinkState
    private lateinit var _drinkList: List<Drink>

    init {
        fetchDrinkFilter()
    }

    fun fetchDrinkFilter() {
        viewModelScope.launch {
            filterUseCaseImp.getFilters().collect {
                when (it) {
                    is Resource.Loading -> {
                        _getFilterState.value = State(isLoading = true)
                    }
                    is Resource.Success -> {
                        _filterList =
                            it.data?.map { filterData -> filterMapperUI.mapToOut(filterData) }
                                ?: listOf()
                        _getFilterState.value = State(data = _filterList)
                    }
                    is Resource.Error -> {
                        _getFilterState.value =
                            State(error = errorViewMapper.mapToOut(it.errorEntity))
                    }
                }
            }
        }
    }

    fun fetchDrinks(category: String) {
        viewModelScope.launch {
            drinksUseCaseImp.fetchDrinksByCategory(category).collect {
                when (it) {
                    is Resource.Loading -> {
                        _getDrinkState.value = State(isLoading = true)
                    }
                    is Resource.Success -> {
                        _drinkList =
                            it.data?.map { drinksData -> drinksMapperUI.mapToOut(drinksData) }
                                ?: listOf()
                        _getDrinkState.value = State(data = _drinkList)
                    }
                    is Resource.Error -> {
                        _getDrinkState.value =
                            State(error = errorViewMapper.mapToOut(it.errorEntity))
                    }
                }
            }
        }
    }

    fun setDrinkCategory(category: String) {
        defaultCategory = category
    }
}