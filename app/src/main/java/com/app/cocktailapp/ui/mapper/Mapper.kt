package com.app.cocktailapp.ui.mapper

interface Mapper<out O, in I> {
    fun mapToOut(input: I): O
}