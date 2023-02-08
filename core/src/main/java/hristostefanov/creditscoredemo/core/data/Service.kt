package hristostefanov.creditscoredemo.core.data

import androidx.annotation.AnyThread
import com.squareup.moshi.JsonDataException
import hristostefanov.creditscoredemo.core.data.models.Response
import retrofit2.HttpException
import retrofit2.http.GET
import java.io.IOException

@AnyThread // The implementation must be main-safe.
internal interface Service {
    @Throws(JsonDataException::class, HttpException::class, IOException::class)
    @GET("/endpoint.json")
    suspend fun getResponse(): Response
}