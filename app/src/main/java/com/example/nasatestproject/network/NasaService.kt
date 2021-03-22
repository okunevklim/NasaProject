package com.example.nasatestproject.network

import com.example.nasatestproject.models.NasaResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getNasaPhotos(
        //@Query("sol") sol: Int,
        @Query("earth_date") earthDate: String,
        @Query("page") pageNumber: Long,
        @Query("api_key") apiKey: String
    ): Single<NasaResponse>
}