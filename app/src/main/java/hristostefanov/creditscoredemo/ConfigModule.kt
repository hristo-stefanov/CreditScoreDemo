package hristostefanov.creditscoredemo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hristostefanov.creditscoredemo.common.BaseUrl

@InstallIn(SingletonComponent::class)
@Module
object ConfigModule {
    @Provides
    @BaseUrl
    fun provideBaseUrl() = BuildConfig.SERVICE_BASE_URL
}