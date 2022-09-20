package com.app.cocktailapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.cocktailapp.databinding.CategoryChipBinding
import com.app.cocktailapp.databinding.FragmentDrinksBinding
import com.app.cocktailapp.ui.adapter.DrinksAdapter
import com.app.cocktailapp.ui.base.BaseFragment
import com.app.cocktailapp.ui.expresso.EspressoIdlingResource
import com.app.cocktailapp.ui.model.Drink
import com.app.cocktailapp.ui.model.Filter
import com.app.cocktailapp.ui.model.State
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrinksFragment : BaseFragment() {

    private var _binding: FragmentDrinksBinding? = null
    private val binding get() = _binding!!
    private val drinksViewModel by viewModels<DrinksViewModel>()
    private var drinksAdapter = DrinksAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrinksBinding.inflate(inflater, container, false)
        Log.d("", "DrinksFragment onCreateView")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("", "DrinksFragment onDestroyView")
        //_binding = null
    }
    override fun onStart() {
        Log.d("", "DrinksFragment onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("", "DrinksFragment onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("", "DrinksFragment onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("", "DrinksFragment onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("", "DrinksFragment onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("", "DrinksFragment onDetach")
        super.onDetach()
    }

    override fun subscribeUi() {
        Log.d("", "DrinksFragment onViewCreated")
        Log.d("", "subscribeUi")
        setupChip()
        adapterInit()
        observeDrinksData()
    }

    private fun setupChip() {
        EspressoIdlingResource.increment()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                drinksViewModel.getFilterState.collectLatest { uiState ->
                    uiState.data?.run {
                        if (uiState.data.isNotEmpty()) {
                            Log.d("", "subscribeUi Chip")
                            chipInitialization(uiState.data.toMutableList())
                        }
                    }
                }
            }
        }
    }

    private fun chipInitialization(categories: List<Filter>) {
        for (category in categories) {
            val chip = createChip(category.strCategory)
            //This should move to VM
            if (chip.text.toString() == drinksViewModel.defaultCategory) {
                chip.isChecked = true
                drinksViewModel.setDrinkCategory(chip.text.toString())
            }

            chip.setOnClickListener { view ->
                val text = (view as Chip).text.toString()
                if (text != drinksViewModel.defaultCategory) {
                    drinksViewModel.setDrinkCategory(chip.text.toString())
                    drinksViewModel.fetchDrinks(drinksViewModel.defaultCategory)
                } else {
                    chip.isChecked = true
                    drinksViewModel.setDrinkCategory(chip.text.toString())
                }
            }

            binding.drinkCategory.addView(chip)
        }

        if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
            EspressoIdlingResource.decrement()
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
        onItemClicked()
    }

    private fun observeDrinksData() {
        EspressoIdlingResource.increment()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                drinksViewModel.getDrinkState.collectLatest { uiState ->
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
            drinksViewModel.fetchDrinks(drinksViewModel.defaultCategory)
        }
    }

    private fun onSuccessState(state: State<List<Drink>>) {
        state.data?.run {
            if (state.data.isEmpty()) {
                binding.noResultFound.visibility = View.VISIBLE
            } else {
                binding.noResultFound.visibility = View.GONE
                drinksAdapter.update(state.data.toMutableList())

                if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
            }
            binding.included.loading.visibility = View.GONE
        }

    }

    private fun onErrorState(state: State<List<Drink>>) {
        state.error?.run {
            binding.noResultFound.visibility = View.GONE
            binding.included.loading.visibility = View.GONE
            showMessage(state.error.message)
        }
    }

    private fun onLoadingState(state: State<List<Drink>>) {
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