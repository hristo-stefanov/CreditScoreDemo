package hristostefanov.creditscoredemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import hristostefanov.creditscoredemo.common.Registry

@HiltAndroidApp
class App : Application() {
    init {
        Registry.SERVICE_BASE_URL = BuildConfig.SERVICE_BASE_URL
    }
}