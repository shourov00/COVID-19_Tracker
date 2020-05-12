package com.sfazleyrabbi.covid19tracker.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sfazleyrabbi.covid19tracker.persistence.AppDatabase
import com.sfazleyrabbi.covid19tracker.persistence.AppDatabase.Companion.DATABASE_NAME
import com.sfazleyrabbi.covid19tracker.persistence.CountryDao
import com.sfazleyrabbi.covid19tracker.util.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@Module
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }


    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }


    @JvmStatic
    @Singleton
    @Provides
    fun provideAppDB(app: Application): AppDatabase{
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideInstance(app: Application, requestOptions: RequestOptions): RequestManager{
        return Glide.with(app)
            .applyDefaultRequestOptions(requestOptions)
    }

}