package hristostefanov.creditscoredemo.core.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hristostefanov.creditscoredemo.common.BaseUrl
import hristostefanov.creditscoredemo.core.business.ReportCreditScoreProgressInteractor
import hristostefanov.creditscoredemo.core.business.ReportCreditScoreProgressInteractorImpl
import hristostefanov.creditscoredemo.core.business.dependencies.CreditScoreRepository
import hristostefanov.creditscoredemo.core.data.CreditScoreRepositoryImpl
import hristostefanov.creditscoredemo.core.data.Service
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal abstract class CoreModule {
    companion object {
        @Singleton
        @Provides
        fun provideService(
            @BaseUrl
            baseUrl: String
        ): Service {
            val moshi = Moshi.Builder()
                // handles Kotlin nullability
                .add(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            return retrofit.create(Service::class.java)
        }
    }

    @Singleton
    @Binds
    abstract fun bindCreditScoreRepository(impl: CreditScoreRepositoryImpl): CreditScoreRepository

    @Singleton
    @Binds
    abstract fun bindReportCreditScoreProgressInteractor(impl: ReportCreditScoreProgressInteractorImpl)
            : ReportCreditScoreProgressInteractor
}