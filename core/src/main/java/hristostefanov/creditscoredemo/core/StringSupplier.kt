package hristostefanov.creditscoredemo.core

import androidx.annotation.StringRes

interface StringSupplier {
    fun getString(@StringRes resId: Int): String
}