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
import com.app.cocktailapp.R
import com.app.cocktailapp.databinding.CategoryChipBinding
import com.app.cocktailapp.databinding.FragmentDrinksBinding
import com.app.cocktailapp.ui.adapter.DrinksAdapter
import com.app.cocktailapp.ui.base.BaseFragment
import com.app.cocktailapp.ui.extension.makeInvisible
import com.app.cocktailapp.ui.extension.makeVisible
import com.app.cocktailapp.ui.model.Filter
import com.app.cocktailapp.ui.model.UiState
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrinksFragment : BaseFragment() {

    private lateinit var binding: FragmentDrinksBinding
    private val drinksViewModel by viewModels<DrinksViewModel>()
    private val drinksAdapter = DrinksAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDrinksBinding.inflate(inflater, container, false)
        return binding.root
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
                drinksViewModel.getFilterUiState.collectLatest {
                    when (it) {
                        is UiState.InitialState -> {
                            drinksViewModel.fetchDrinkFilter()
                        }
                        is UiState.ShowEmptyData -> {
                            showMessage(getString(R.string.nothing_found))
                        }
                        is UiState.ShowData -> {
                            if(binding.drinkCategory.isEmpty()) {
                                chipInitialization(it.data.toMutableList())
                            }
                        }
                        is UiState.ShowError -> {
                            showMessage(it.error.message)
                        }
                    }
                }
            }
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
                drinksViewModel.getDrinkUiState.collectLatest {
                    when (it) {
                        is UiState.InitialState -> {
                            drinksViewModel.fetchDrinks(drinksViewModel.defaultCategory)
                        }
                        is UiState.ShowLoading -> {
                            binding.noResultFound.makeInvisible()
                            binding.included.loading.makeVisible()
                        }
                        is UiState.ShowEmptyData -> {
                            binding.included.loading.makeInvisible()
                            binding.included.loading.makeVisible()
                            showMessage(getString(R.string.nothing_found))
                        }
                        is UiState.ShowData -> {
                            binding.included.loading.makeInvisible()
                            drinksAdapter.update(it.data.toMutableList())
                        }
                        is UiState.ShowError -> {
                            binding.included.loading.makeInvisible()
                            showMessage(it.error.message)
                        }
                    }
                }
            }
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