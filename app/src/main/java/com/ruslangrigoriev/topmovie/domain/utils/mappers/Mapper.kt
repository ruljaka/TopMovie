package com.ruslangrigoriev.topmovie.domain.utils.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}