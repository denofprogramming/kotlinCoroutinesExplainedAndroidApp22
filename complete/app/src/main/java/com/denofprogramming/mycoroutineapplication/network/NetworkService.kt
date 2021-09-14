package com.denofprogramming.mycoroutineapplication.network

import com.denofprogramming.mycoroutineapplication.shared.domain.ImageDetail
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://dog.ceo"


interface NetworkService {

    @GET("/api/breeds/image/random")
    suspend fun getImage(): Response<ImageDetail>

}


/**
 * Use the Retrofit builder to build a retrofit object.
 */
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object NetworkServiceApi {
    val retrofitService: NetworkService by lazy { retrofit.create(NetworkService::class.java) }


}





