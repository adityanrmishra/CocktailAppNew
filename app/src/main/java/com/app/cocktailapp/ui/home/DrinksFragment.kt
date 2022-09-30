package com.app.cocktailapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.cocktailapp.databinding.CategoryChipBinding
import com.app.cocktailapp.databinding.FragmentDrinksBinding
import com.app.cocktailapp.ui.adapter.DrinksAdapter
import com.app.cocktailapp.ui.base.BaseFragment
import com.app.cocktailapp.ui.model.Drink
import com.app.cocktailapp.ui.model.Filter
import com.app.cocktailapp.ui.model.State
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrinksFragment : BaseFragment() {

    private lateinit var binding: FragmentDrinksBinding
    private val drinksViewModel by viewModels<DrinksViewModel>()
    private var drinksAdapter = DrinksAdapter()
    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        if (root == null) {
            binding = FragmentDrinksBinding.inflate(inflater, container, false)
            root = binding.root
        }
        return root as View
    }

    override fun subscribeUi() {
        setupChip()
        adapterInit()
        onItemClicked()
        observeDrinksData()
    }

    private fun setupChip() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                drinksViewModel.getFilterState.collectLatest { uiState ->
                    onInitialStateChip(uiState)
                    onSuccessStateChip(uiState)
                    onErrorStateChip(uiState)
                }
            }
        }
    }

    private fun onInitialStateChip(result: State<List<Filter>>) {
        if (result.isInitialState) {
            drinksViewModel.fetchDrinkFilter()
        }
    }

    private fun onSuccessStateChip(result: State<List<Filter>>) {
        result.data?.run {
            if (result.data.isNotEmpty() && binding.drinkCategory.isEmpty()) {
                chipInitialization(result.data.toMutableList())
            }
        }
    }

    private fun onErrorStateChip(result: State<List<Filter>>) {
        result.error?.run {
            showMessage(result.error.message)
        }
    }

    private fun chipInitialization(categories: List<Filter>) {
        for (category in categories) {
            val chip = createChip(category.strCategory)
            chip.isChecked = drinksViewModel.isCheckedChip(chip.text.toString())
            chip.setOnClickListener { view ->
                val text = (view as Chip).text.toString()
                when {
                    !drinksViewModel.isCheckedChip(text) -> {
                        drinksViewModel.setDrinkCategory(text)
                        drinksViewModel.fetchDrinks(drinksViewModel.defaultCategory)
                    }
                }
                chip.isChecked = drinksViewModel.isCheckedChip(text)
            }
            binding.drinkCategory.addView(chip)
        }
    }

    private fun createChip(label: String): Chip {
        val chip = CategoryChipBinding.inflate(layoutInflater).root
        chip.text = label
        return chip
    }

    private fun adapterInit() {
        binding.drinkRecyclerView.apply {
            adapter = drinksAdapter
        }
    }

    private fun observeDrinksData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                drinksViewModel.getDrinkState.collectLatest { uiState ->
                    onInitialStateAdapter(uiState)
                    onLoadingStateAdapter(uiState)
                    onErrorStateAdapter(uiState)
                    onSuccessStateAdapter(uiState)
                }
            }
        }
    }

    private fun onInitialStateAdapter(result: State<List<Drink>>) {
        if (result.isInitialState) {
            drinksViewModel.fetchDrinks(drinksViewModel.defaultCategory)
        }
    }

    private fun onSuccessStateAdapter(state: State<List<Drink>>) {
        state.data?.run {
            if (state.data.isEmpty()) {
                binding.noResultFound.visibility = View.VISIBLE
            } else {
                binding.noResultFound.visibility = View.GONE
                drinksAdapter.update(state.data.toMutableList())
            }
            binding.included.loading.visibility = View.GONE
        }
    }

    private fun onErrorStateAdapter(state: State<List<Drink>>) {
        state.error?.run {
            binding.noResultFound.visibility = View.GONE
            binding.included.loading.visibility = View.GONE
            showMessage(state.error.message)
        }
    }

    private fun onLoadingStateAdapter(state: State<List<Drink>>) {
        if (state.isLoading) {
            binding.noResultFound.visibility = View.GONE
            binding.included.loading.visibility = View.VISIBLE
        }
    }

    private fun onItemClicked() {
        drinksAdapter.itemClickListener {
            findNavController().navigate(
                DrinksFragmentDirections.actionDrinksFragmentToDrinkInfoFragment(
                    it.idDrink, it.strDrink
                )
            )
        }
    }
}