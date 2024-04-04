package com.oba.monietracker.data.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.oba.monietracker.data.models.Photo
import com.oba.monietracker.data.models.PhotosData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoViewModel(): ViewModel() {

    private val clientId: String = "u0K1UKic1vFMPLxqfcrSYAfbO6JJRF8l66tPhOcEq70"
    private val photosCount = 12

    private var _photosResponse = mutableStateOf<List<Photo>>(emptyList())
    val photosResponse: MutableState<List<Photo>>
        @Composable get() = remember { _photosResponse }

    private var _singlePhotoResponse = mutableStateOf<Photo>(Photo())
    val singlePhotoResponse: MutableState<Photo>
        @Composable get() = remember { _singlePhotoResponse }

    //private fun getPhotosBySearchQuery(database: AppDatabase) {
    suspend fun getPhotosBySearchQuery(query: String) {
        val service = Api.photoRetrofitService.getPhotosBySearchQuery(clientId, query)

        service.enqueue(object : Callback<PhotosData> {
            override fun onResponse(
                call: Call<PhotosData>,
                response: Response<PhotosData>
            )
            {
                if (response.isSuccessful) {
                    Log.i("GetPhotos-VM", response.body()?.results.toString())
                    _photosResponse.value = (response.body()?.results?:emptyList<Photo>())
                            as List<Photo>
                    //Log.i("DataStream", _photosResponse.toString())

                    // store this data in the local db
//                    GlobalScope.launch {
//                        saveDataToDatabase(database = database, _photosResponse.value)
//                    }
                }
            }

            override fun onFailure(call: Call<PhotosData>, t: Throwable) {
                Log.e("Err-GetPhotos-VM", "${t.message}")
            }
        })
    }

    // get single photo from API
    suspend fun getSinglePhoto(photoId: String) {
    val service = Api.photoRetrofitService.getSinglePhoto(clientId, photoId)

    service.enqueue(object : Callback<Photo> {
        override fun onResponse(
            call: Call<Photo>,
            response: Response<Photo>
        )
        {
            if (response.isSuccessful) {
                Log.i("GetOnePhoto-VM", response.body().toString())
                _singlePhotoResponse.value = response.body()?: Photo()
                //Log.i("DataStream", _singlePhotoResponse.toString())
            }
        }

        override fun onFailure(call: Call<Photo>, t: Throwable) {
            Log.d("Err-GetOnePhoto-VM", "${t.message}")
        }
    })
}
}