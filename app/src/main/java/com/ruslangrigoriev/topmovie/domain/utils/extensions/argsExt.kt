package com.ruslangrigoriev.topmovie.domain.utils.extensions

import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty

fun stringArgs(key: String): ReadOnlyProperty<Fragment, String> {
    return ReadOnlyProperty { thisRef, _ ->
        val args = thisRef.requireArguments()
        require(args.containsKey(key)) { "Arguments don't contain key '$key'" }
        requireNotNull(args.getString(key))
    }
}

fun intArgs(key: String): ReadOnlyProperty<Fragment, Int> {
    return ReadOnlyProperty { thisRef, _ ->
        val args = thisRef.requireArguments()
        require(args.containsKey(key)) { "Arguments don't contain key '$key'" }
        requireNotNull(args.getInt(key))
    }
}