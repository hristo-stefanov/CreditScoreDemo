package hristostefanov.creditscoredemo.ui

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UIModule {
    companion object {
        @Provides
        fun provideStringSupplier(application: Application): StringSupplier {
            return object : StringSupplier {
                override fun getString(resId: Int) = application.getString(resId)
            }
        }
    }
}