package com.nuhlp.nursehelper.data.network

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nuhlp.nursehelper.data.network.model.NullToEmptyStringAdapter
import com.nuhlp.nursehelper.data.network.model.address.AddressResponse
import com.nuhlp.nursehelper.data.network.model.place.PlaceResponse
import com.nuhlp.nursehelper.data.room.app.AppDatabase
import com.nuhlp.nursehelper.utill.useapp.Constants.BASE_URL
import com.nuhlp.nursehelper.utill.useapp.Constants.REST_API_KEY
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(NullToEmptyStringAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/* 원래 사용하던 것 */
@Deprecated("getAppKakaoApi()")
object KaKaoApi {
    val retrofitService : MapsApiService by lazy {
        retrofit.create(MapsApiService::class.java)
    }
}

private lateinit var INSTANCE: MapsApiService

fun getAppKakaoApi(): MapsApiService {
    synchronized(MapsApiService::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build().create(MapsApiService::class.java)
        }

    }
    return INSTANCE
}


interface MapsApiService {
    @Headers("Authorization: KakaoAK $REST_API_KEY")
    @GET("v2/local/search/address.json")
    suspend fun getLocations(
        @Query("query") keyword: String,
        @Query("analyze_type") sort: String = "similar",
        @Query("page") page: Int=1,
        @Query("size") size: Int = 10
    ): AddressResponse

    @Headers("Authorization: KakaoAK $REST_API_KEY")
    @GET("/v2/local/search/category.json")
    suspend fun getPlaces(
        @Query("category_group_code") category: String,
        @Query("y") latitude: Double ,
        @Query("x") longitude: Double ,
        @Query("radius") radius: Int = 100,
        @Query("page") page: Int=1,
        @Query("size") size: Int = 10
    ): PlaceResponse


}