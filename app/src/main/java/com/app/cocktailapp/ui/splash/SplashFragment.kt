package com.app.cocktailapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.cocktailapp.R
import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.databinding.FragmentSplashBinding
import com.app.cocktailapp.ui.base.BaseFragment
import com.app.cocktailapp.ui.extension.makeInvisible
import com.app.cocktailapp.ui.extension.makeVisible

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
        splashViewModel.isOk.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.included.loading.makeVisible()
                }
                is Resource.Success -> {
                    binding.included.loading.makeInvisible()
                    binding.appText.makeInvisible()
                    gotoCocktailDrinks()
                }
                is Resource.Error -> {
                    binding.included.loading.makeInvisible()
                    showMessage(R.string.unknown_error.toString())
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