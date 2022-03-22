package com.ruslangrigoriev.topmovie.presentation.adapters

import android.view.View

interface BindingInterface<T : Any> {
    fun bindData(item: T, view: View)
}