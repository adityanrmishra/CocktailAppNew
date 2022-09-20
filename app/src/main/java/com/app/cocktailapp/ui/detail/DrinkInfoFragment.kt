package com.app.cocktailapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.cocktailapp.databinding.FragmentDrinkInfoBinding
import com.app.cocktailapp.ui.expresso.EspressoIdlingResource
import com.app.cocktailapp.ui.base.BaseFragment
import com.app.cocktailapp.ui.extension.setImageUrl
import com.app.cocktailapp.ui.model.DrinkInfo
import com.app.cocktailapp.ui.model.State
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrinkInfoFragment : BaseFragment() {

    private var _binding: FragmentDrinkInfoBinding? = null
    private val binding get() = _binding!!
    private val drinkInfoViewModel by viewModels<DrinkInfoViewModel>()
    private val args: DrinkInfoFragmentArgs by navArgs()

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
        binding.tvHeaderTitle.text = args.drinkTitle.toString()
        observeDrinkData()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeDrinkData() {
        EspressoIdlingResource.increment()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                drinkInfoViewModel.getDrinkInfoState.collectLatest { result ->
                    onInitialState(result)
                    onLoadingState(result)
                    onSuccessState(result)
                    onErrorState(result)
                }
            }
        }
    }

    private fun onInitialState(result: State<List<DrinkInfo>>) {
        if (result.isInitialState) {
            drinkInfoViewModel.fetchDrink(args.drinkId.toString())
        }
    }

    private fun onSuccessState(result: State<List<DrinkInfo>>) {
        result.data?.run {
            updateProgress(false)
            updateUI(result.data.get(0))
            if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    private fun onLoadingState(result: State<List<DrinkInfo>>) {
        if (result.isLoading) {
            updateProgress(true)
        }
    }

    private fun onErrorState(result: State<List<DrinkInfo>>) {
        result.error?.run {
            updateProgress(false)
            showMessage(result.error.message)
            //Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProgress(showProgress: Boolean) {
        if (showProgress) {
            binding.included.loading.visibility = View.VISIBLE
        } else {
            binding.included.loading.visibility = View.INVISIBLE
        }
    }

    private fun updateUI(drinkInfo: DrinkInfo) {
        setImageUrl(binding.ivDrinkIcon, drinkInfo.strDrinkThumb, true)
        binding.tvAlias.text = drinkInfo.strDrink
        binding.tvId.text = drinkInfo.idDrink
        binding.tvCategory.text = drinkInfo.strCategory
        binding.tvAlcoholic.text = drinkInfo.strAlcoholic
        binding.tvGlassType.text = drinkInfo.strGlass
        binding.tvUpdatedDate.text = drinkInfo.dateModified
        binding.tvIngredient.text = drinkInfo.strIngredient1
        binding.tvMeasure.text = drinkInfo.strMeasure1
        binding.tvInstructions.text = drinkInfo.strInstructions
    }

}