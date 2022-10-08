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
import com.app.cocktailapp.R
import com.app.cocktailapp.databinding.FragmentDrinkInfoBinding
import com.app.cocktailapp.ui.base.BaseFragment
import com.app.cocktailapp.ui.extension.*
import com.app.cocktailapp.ui.model.DrinkInfo
import com.app.cocktailapp.ui.model.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrinkInfoFragment : BaseFragment() {

    private var _binding: FragmentDrinkInfoBinding? = null
    private val binding get() = _binding!!
    private val drinkInfoViewModel by viewModels<DrinkInfoViewModel>()
    private val args: DrinkInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                drinkInfoViewModel.getDrinkInfoUiState.collectLatest {
                    when (it) {
                        is UiState.InitialState -> {
                            drinkInfoViewModel.fetchDrink(args.drinkId.toString())
                        }
                        is UiState.ShowLoading -> {
                            binding.included.loading.makeVisible()
                        }
                        is UiState.ShowEmptyData -> {
                            binding.included.loading.makeInvisible()
                            showMessage(getString(R.string.nothing_found))
                        }
                        is UiState.ShowData -> {
                            binding.included.loading.makeInvisible()
                            updateUI(it.data)
                        }
                        is UiState.ShowError -> {
                            binding.included.loading.makeInvisible()
                            showMessage(it.error.message.toString())
                        }
                    }
                }

            }
        }
    }

    private fun updateUI(drinkInfo: DrinkInfo) {
        binding.ivDrinkIcon.loadImageWithPicassoExt(drinkInfo.strDrinkThumb, true)
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