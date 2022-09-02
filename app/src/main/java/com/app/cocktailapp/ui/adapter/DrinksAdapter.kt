package com.app.cocktailapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.cocktailapp.databinding.ItemDrinkBinding
import com.app.cocktailapp.ui.extension.setOnSafeClickListener
import com.app.cocktailapp.ui.model.Drink
import javax.inject.Inject

class DrinksAdapter @Inject constructor(
    private val list: ArrayList<Drink>,
    private val onStarClick: (details: Drink, view: View) -> Unit
) : RecyclerView.Adapter<DrinksAdapter.DrinkHolder>() {

    inner class DrinkHolder(private val binding: ItemDrinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Drink) {
            binding.item = item
            binding.position = adapterPosition
            binding.root.setOnSafeClickListener {
                onStarClick.invoke(item, binding.profPic)
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

    fun update(newList: List<Drink>) {
        list.clear()
        list.addAll(newList)
        notifyItemRangeChanged(0, list.size)
        notifyDataSetChanged()
    }
}