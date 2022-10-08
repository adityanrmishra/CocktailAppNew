package com.app.cocktailapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.cocktailapp.databinding.ItemDrinkBinding
import com.app.cocktailapp.ui.extension.DrinkDiffUtilCallback
import com.app.cocktailapp.ui.extension.loadImageWithPicassoExt
import com.app.cocktailapp.ui.model.Drink
import javax.inject.Inject

class DrinksAdapter @Inject constructor() : RecyclerView.Adapter<DrinksAdapter.DrinkHolder>() {

    private val list = mutableListOf<Drink>()
    private var listener: ((Drink) -> Unit)? = null

    inner class DrinkHolder(private val binding: ItemDrinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Drink) {
            with(item) {
               binding.root.setOnClickListener {
                    listener?.let {
                        it(item)
                    }
                }
                binding.tvTitle.text = this.idDrink
                binding.tvName.text = this.strDrink
                binding.ivDrink.loadImageWithPicassoExt(this.strDrinkThumb, true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDrinkBinding.inflate(inflater, parent, false)
        return DrinkHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun itemClickListener(drink: (Drink) -> Unit) {
        listener = drink
    }

    fun update(newList: List<Drink>) {
        val diffResult = DiffUtil.calculateDiff(DrinkDiffUtilCallback(list, newList))
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}