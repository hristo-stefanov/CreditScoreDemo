package hristostefanov.creditscoredemo.di

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hristostefanov.creditscoredemo.BuildConfig
import hristostefanov.creditscoredemo.StringSupplier
import hristostefanov.creditscoredemo.business.ReportCreditScoreProgressInteractor
import hristostefanov.creditscoredemo.business.ReportCreditScoreProgressInteractorImpl
import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import hristostefanov.creditscoredemo.data.CreditScoreRepositoryImpl
import hristostefanov.creditscoredemo.data.Service
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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
                .baseUrl(BuildConfig.SERVICE_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        @Provides
        fun provideService(retrofit: Retrofit): Service = retrofit.create(Service::class.java)

        @Provides
        fun provideStringSupplier(application: Application): StringSupplier {
            return object : StringSupplier {
                override fun getString(resId: Int, vararg formatArgs: Any) = application.getString(resId, * formatArgs)
            }
        }
    }

    @Binds
    abstract fun bindCreditScoreRepository(impl: CreditScoreRepositoryImpl): CreditScoreRepository

    @Binds
    abstract fun bindReportCreditScoreProgressInteractor(impl: ReportCreditScoreProgressInteractorImpl)
            : ReportCreditScoreProgressInteractor
}