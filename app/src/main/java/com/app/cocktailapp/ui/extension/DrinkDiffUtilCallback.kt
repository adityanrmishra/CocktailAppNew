package com.app.cocktailapp.ui.extension

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.app.cocktailapp.ui.model.Drink

class DrinkDiffUtilCallback(private val oldList: List<Drink>, private val newList: List<Drink>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idDrink == newList[newItemPosition].idDrink
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].idDrink == newList[newItemPosition].idDrink -> true
            oldList[oldItemPosition].strDrink == newList[newItemPosition].strDrink -> true
            oldList[oldItemPosition].strDrinkThumb == newList[newItemPosition].strDrinkThumb -> true
            else -> false
        }
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}