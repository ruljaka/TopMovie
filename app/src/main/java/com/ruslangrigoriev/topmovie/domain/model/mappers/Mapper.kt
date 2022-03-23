package com.ruslangrigoriev.topmovie.domain.model.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}