package hristostefanov.creditscoredemo

import androidx.annotation.StringRes

interface StringSupplier {
    fun getString(@StringRes resId: Int): String
}