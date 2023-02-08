package hristostefanov.creditscoredemo

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import hristostefanov.creditscoredemo.common.BaseUrl
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [ConfigModule::class])
@Module
object TestConfigModule {
    // NOTE: Even though it's scope, a new SingletonComponent is created for each test so we
    // get a new instance for each test.
    // We need to create it here so as we can provide @BaseUrl
    @Provides
    @Singleton
    fun provideMockWebServer() = MockWebServer()

    @Provides
    @BaseUrl
    fun provideBaseUrl(mockWebServer: MockWebServer) = mockWebServer.url("/").toString()
}