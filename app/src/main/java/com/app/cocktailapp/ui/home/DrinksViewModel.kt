package com.app.cocktailapp.ui.home

import androidx.lifecycle.viewModelScope
import com.app.cocktailapp.ui.base.BaseViewModel
import com.app.cocktailapp.core.Resource
import com.app.cocktailapp.domain.usecase.DrinkFilterUseCase
import com.app.cocktailapp.domain.usecase.DrinksUseCase
import com.app.cocktailapp.ui.mapper.DrinkMapperUI
import com.app.cocktailapp.ui.mapper.ErrorMapperUI
import com.app.cocktailapp.ui.mapper.FilterMapperUI
import com.app.cocktailapp.ui.model.Drink
import com.app.cocktailapp.ui.model.Filter
import com.app.cocktailapp.ui.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DrinksViewModel @Inject constructor(
    private val drinkFilterUseCase: DrinkFilterUseCase,
    private val filterMapperUI: FilterMapperUI,
    private val drinksUseCase: DrinksUseCase,
    private val drinkMapperUI: DrinkMapperUI,
    private val errorViewMapper: ErrorMapperUI,
) :
    BaseViewModel() {

    var defaultCategory = "Cocktail"

    private val _getFilterState =
        MutableStateFlow(State<List<Filter>>(isInitialState = true))
    val getFilterState: StateFlow<State<List<Filter>>> = _getFilterState
    private lateinit var _filterList: List<Filter>

    private val _getDrinksState =
        MutableStateFlow(State<List<Drink>>(isInitialState = true))
    val getDrinksState: StateFlow<State<List<Drink>>> = _getDrinksState
    private lateinit var _drinksList: List<Drink>


    init {
        setDrinkCategory(defaultCategory)
        fetchDrinkFilter()
    }

    fun fetchDrinkFilter() {
        drinkFilterUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _getFilterState.value = State(isLoading = true)
                }
                is Resource.Success -> {
                    _filterList =
                        it.data?.map { memeData -> filterMapperUI.mapToOut(memeData) } ?: listOf()
                    _getFilterState.value = State(data = _filterList)
                }
                is Resource.Error -> {
                    _getFilterState.value =
                        State(error = errorViewMapper.mapToOut(it.errorEntity))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun fetchDrinks() {
        drinksUseCase(defaultCategory).onEach {
            when (it) {
                is Resource.Loading -> {
                    _getDrinksState.value = State(isLoading = true)
                }
                is Resource.Success -> {
                    _drinksList =
                        it.data?.map { drinkData -> drinkMapperUI.mapToOut(drinkData) } ?: listOf()
                    _getDrinksState.value = State(data = _drinksList)
                }
                is Resource.Error -> {
                    _getDrinksState.value =
                        State(error = errorViewMapper.mapToOut(it.errorEntity))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setDrinkCategory(category: String) {
        defaultCategory = category
    }
}