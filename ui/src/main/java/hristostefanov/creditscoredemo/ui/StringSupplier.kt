package hristostefanov.creditscoredemo.ui

import androidx.annotation.StringRes

interface StringSupplier {
    fun getString(@StringRes resId: Int): String
}