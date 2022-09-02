package com.app.cocktailapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.app.cocktailapp.R
import com.app.cocktailapp.databinding.CategoryChipBinding
import com.app.cocktailapp.databinding.FragmentDrinksBinding
import com.app.cocktailapp.ui.adapter.DrinksAdapter
import com.app.cocktailapp.ui.base.BaseFragment
import com.app.cocktailapp.ui.detail.DrinkInfoFragment
import com.app.cocktailapp.ui.model.Drink
import com.app.cocktailapp.ui.model.Filter
import com.app.cocktailapp.ui.model.State
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DrinksFragment : BaseFragment() {

    private var _binding: FragmentDrinksBinding? = null
    private val binding get() = _binding!!
    private val drinksViewModel by viewModels<DrinksViewModel>()

    @Inject
    lateinit var drinksAdapter: DrinksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDrinksBinding.inflate(inflater, container, false).let {
        _binding = it
        with(it) {
            headerTitle = getString(R.string.cocktail_drinks)
            root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun subscribeUi() {
        setupChip()
        adapterInit()
        observeResultState()
    }

    private fun setupChip() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                drinksViewModel.getFilterState.collectLatest { uiState ->
                    uiState.data?.let { result ->
                        if (result.isNotEmpty()) {
                            chipInitialization(result.toMutableList())
                        }
                    }
                }
            }
        }
    }

    private fun chipInitialization(categories: List<Filter>) {
        for (category in categories) {
            val chip = createChip(category.strCategory.toString())
            if (chip.text.toString() == drinksViewModel.defaultCategory) {
                chip.isChecked = true
            }
            binding.drinkCategory.addView(chip)
        }

        binding.drinkCategory.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let {
                drinksViewModel.setDrinkCategory(chip.text.toString())
                drinksViewModel.fetchDrinks()
            } ?: kotlin.run {
            }
        }
    }

    private fun createChip(label: String): Chip {
        val chip = CategoryChipBinding.inflate(layoutInflater).root
        chip.text = label
        return chip
    }

    private fun adapterInit() {
        drinksAdapter = DrinksAdapter(arrayListOf(), onDrinkClick)
        binding.drinkRecyclerView.apply {
            adapter = drinksAdapter
        }
    }

    private fun observeResultState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                drinksViewModel.getDrinksState.collectLatest { uiState ->
                    onInitialState(uiState)
                    onLoadingState(uiState)
                    onErrorState(uiState)
                    onSuccessState(uiState)
                }
            }
        }
    }

    private fun onInitialState(result: State<List<Drink>>) {
        if (result.isInitialState) {
            drinksViewModel.fetchDrinks()
        }
    }

    private fun onSuccessState(state: State<List<Drink>>) {
        state.data?.let { result ->
            if (result.isEmpty()) {
                binding.noResultFound.visibility = View.VISIBLE
            } else {
                binding.noResultFound.visibility = View.GONE
                drinksAdapter.update(result.toMutableList())
            }
            binding.included.loading.visibility = View.GONE
        }

    }

    private fun onErrorState(state: State<List<Drink>>) {
        state.error?.let {
            binding.noResultFound.visibility = View.GONE
            binding.included.loading.visibility = View.GONE
            Toast.makeText(requireContext(), state.error.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLoadingState(state: State<List<Drink>>) {
        if (state.isLoading) {
            binding.noResultFound.visibility = View.GONE
            binding.included.loading.visibility = View.VISIBLE
        }
    }

    private val onDrinkClick: (star: Drink, view: View) -> Unit =
        { drink, view ->
            val extras = FragmentNavigatorExtras(
                (view to drink.strDrinkThumb) as Pair<View, String>
            )
            findNavController().navigate(
                R.id.action_DrinksFragment_to_DrinkInfoFragment,
                DrinkInfoFragment.Args(drink).toBundle(),
                null,
                extras
            )
        }


}