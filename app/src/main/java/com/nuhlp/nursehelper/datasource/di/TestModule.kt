package com.nuhlp.nursehelper.datasource.di

import com.nuhlp.nursehelper.datasource.network.MapsApiService
import com.nuhlp.nursehelper.datasource.network.model.NullToEmptyStringAdapter
import com.nuhlp.nursehelper.utill.useapp.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(NullToEmptyStringAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): MapsApiService {
        return retrofit.create(MapsApiService::class.java)
    }
}