package com.app.cocktailapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.cocktailapp.R
import com.app.cocktailapp.core.Resource
import com.app.cocktailapp.databinding.FragmentSplashBinding
import com.app.cocktailapp.ui.expresso.EspressoIdlingResource
import com.app.cocktailapp.ui.base.BaseFragment

class SplashFragment : BaseFragment() {

    private var binding: FragmentSplashBinding? = null
    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSplashBinding.inflate(inflater, container, false).let {
        binding = it
        with(it) {
            viewModel = splashViewModel
            lifecycleOwner = this@SplashFragment
            root
        }
    }

    override fun subscribeUi() {
        EspressoIdlingResource.increment()

        splashViewModel.isOk.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding?.included?.loading?.show()
                }
                is Resource.Success -> {
                    binding?.included?.loading?.hide()
                    binding?.appText?.visibility = View.INVISIBLE
                    gotoCocktailDrinks()
                }
                is Resource.Error -> {
                    binding?.included?.loading?.hide()
                    showMessage(R.string.unknown_error.toString())
                }
            }
        }
    }

    private fun gotoCocktailDrinks() {
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        binding?.let {
            findNavController().navigate(R.id.action_SplashFragment_to_DrinksFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}