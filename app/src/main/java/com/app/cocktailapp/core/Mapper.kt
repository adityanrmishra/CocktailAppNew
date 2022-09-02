package com.app.cocktailapp.core

interface Mapper<out O, in I> {
    fun mapToOut(input: I): O
}