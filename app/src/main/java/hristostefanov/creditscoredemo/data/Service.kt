package hristostefanov.creditscoredemo.data

import androidx.annotation.AnyThread
import hristostefanov.creditscoredemo.data.models.Response
import retrofit2.HttpException
import retrofit2.http.GET

@AnyThread // The implementation must be main-safe.
interface Service {
    @Throws(HttpException::class)
    @GET("/endpoint.json")
    suspend fun getResponse(): Response
}