package com.app.cocktailapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.cocktailapp.databinding.FragmentDrinkInfoBinding
import com.app.cocktailapp.ui.base.BaseFragment
import com.app.cocktailapp.ui.model.Drink
import com.app.cocktailapp.ui.model.State
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrinkInfoFragment : BaseFragment() {

    private var _binding: FragmentDrinkInfoBinding? = null
    private val binding get() = _binding!!
    private val drinkInfoViewModel by viewModels<DrinkInfoViewModel>()

    private val drinkItem by lazy { Args.fromBundle(arguments) }

    companion object {
        fun newInstance() = DrinkInfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrinkInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun subscribeUi() {
        observeResultState()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeResultState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                drinkInfoViewModel.getDrinkState.collectLatest { result ->
                    onInitialState(result)
                    onLoadingState(result)
                    onSuccessState(result)
                    onErrorState(result)
                }
            }
        }
    }

    private fun onInitialState(result: State<List<Drink>>) {
        if (result.isInitialState) {
            drinkInfoViewModel.fetchDrinks(drinkItem.drinks.idDrink.toString())
        }
    }

    private fun onSuccessState(result: State<List<Drink>>) {
        result.data?.let {
            updateProgress(false)
            binding.item = it.get(0)
        }
    }

    private fun onLoadingState(result: State<List<Drink>>) {
        if (result.isLoading) {
            updateProgress(true)
        }
    }

    private fun onErrorState(result: State<List<Drink>>) {
        result.error?.let {
            updateProgress(false)
            Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateProgress(showProgress: Boolean) {
        if (showProgress) {
            binding.included.loading.visibility = View.VISIBLE
        } else {
            binding.included.loading.visibility = View.INVISIBLE
        }
    }

    class Args(val drinks: Drink) {
        companion object {
            const val ARG_ITEM = "drinkItem"
            fun fromBundle(bundle: Bundle?): Args {
                if (bundle == null)
                    throw IllegalStateException("Arguments must be passed to fragment")
                val details = bundle.getParcelable<Drink>(ARG_ITEM)
                    ?: throw IllegalStateException("CharacterEntity must be passed as an argument to fragment")
                return Args(details)
            }
        }

        fun toBundle() = bundleOf(ARG_ITEM to drinks)
    }

}