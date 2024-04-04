package com.oba.monietracker.data.api

import com.oba.monietracker.data.models.Photo
import com.oba.monietracker.data.models.PhotosData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {
    // for an api with query params: https://api.com/model?page=1
    @GET("photos/random")
    fun getRandomPhotos(
        @Query(value = "client_id") clientId:String,
        @Query("count") photosCount: Int = 6,
    ): Call<PhotosData>

    @GET("photos/{photoId}/")
    fun getSinglePhoto(
        @Path("photoId") photoId: String,
        @Query(value = "client_id") clientId:String,
    ): Call<Photo>

    @GET("search/photos/")
    fun getPhotosBySearchQuery(
        @Query(value = "client_id") clientId:String,
        @Query(value = "query", encoded = true) query:String,
        @Query(value = "orientation") orientation:String = "landscape"
    ) : Call<PhotosData>
}