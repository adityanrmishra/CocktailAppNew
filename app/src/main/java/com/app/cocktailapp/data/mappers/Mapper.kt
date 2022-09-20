package com.app.cocktailapp.data.mappers

interface Mapper<out O, in I> {
    fun mapToOut(input: I): O
}