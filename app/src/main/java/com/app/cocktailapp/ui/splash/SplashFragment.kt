package com.app.cocktailapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.cocktailapp.R
import com.app.cocktailapp.databinding.FragmentSplashBinding
import com.app.cocktailapp.ui.base.BaseFragment
import com.app.cocktailapp.ui.extension.makeInvisible
import com.app.cocktailapp.ui.extension.makeVisible
import com.app.cocktailapp.ui.model.UiState

class SplashFragment : BaseFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun subscribeUi() {
        splashViewModel.isOk.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.ShowLoading -> {
                    binding.included.loading.makeVisible()
                }
                is UiState.ShowData -> {
                    binding.included.loading.makeInvisible()
                    binding.appText.makeInvisible()
                    gotoCocktailDrinks()
                }
                is UiState.ShowError -> {
                    binding.included.loading.makeInvisible()
                    showMessage(R.string.nothing_found.toString())
                }
            }
        }
    }

    private fun gotoCocktailDrinks() {
        binding.run {
            findNavController().navigate(R.id.action_SplashFragment_to_DrinksFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}