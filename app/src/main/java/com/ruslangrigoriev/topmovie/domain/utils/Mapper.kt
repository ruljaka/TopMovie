package com.ruslangrigoriev.topmovie.domain.utils

interface Mapper<I, O> {
    fun map(input: I): O
}