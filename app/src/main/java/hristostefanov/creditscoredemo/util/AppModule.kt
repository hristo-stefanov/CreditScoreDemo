package hristostefanov.creditscoredemo.util

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hristostefanov.creditscoredemo.core.business.ReportCreditScoreProgressInteractor
import hristostefanov.creditscoredemo.core.business.ReportCreditScoreProgressInteractorImpl
import hristostefanov.creditscoredemo.core.business.dependencies.CreditScoreRepository
import hristostefanov.creditscoredemo.core.data.CreditScoreRepositoryImpl
import hristostefanov.creditscoredemo.core.data.Service
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    companion object {
        @Provides
        fun provideRetrofit(): Retrofit {
            val moshi = Moshi.Builder()
                // handles Kotlin nullability
                .add(KotlinJsonAdapterFactory())
                .build()
            return Retrofit.Builder()
                .baseUrl(Config.SERVICE_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        @Provides
        fun provideService(retrofit: Retrofit): Service = retrofit.create(Service::class.java)

        @Provides
        fun provideStringSupplier(application: Application): StringSupplier {
            return object : StringSupplier {
                override fun getString(resId: Int) = application.getString(resId)
            }
        }
    }

    @Binds
    abstract fun bindCreditScoreRepository(impl: CreditScoreRepositoryImpl): CreditScoreRepository

    @Binds
    abstract fun bindReportCreditScoreProgressInteractor(impl: ReportCreditScoreProgressInteractorImpl)
            : ReportCreditScoreProgressInteractor
}