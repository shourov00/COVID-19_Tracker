package com.sfazleyrabbi.covid19tracker.di.main

import com.sfazleyrabbi.covid19tracker.api.main.CovidApiMainService
import com.sfazleyrabbi.covid19tracker.persistence.AppDatabase
import com.sfazleyrabbi.covid19tracker.persistence.CountryDao
import com.sfazleyrabbi.covid19tracker.repository.main.GlobalRepository
import com.sfazleyrabbi.covid19tracker.repository.main.GlobalRepositoryImpl
import com.sfazleyrabbi.covid19tracker.repository.main.HomeRepository
import com.sfazleyrabbi.covid19tracker.repository.main.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@FlowPreview
@Module
object MainModule {

    @JvmStatic
    @MainScope
    @Provides
    fun provideCovidApiMainService(retrofitBuilder: Retrofit.Builder): CovidApiMainService {
        return retrofitBuilder.build()
            .create(CovidApiMainService::class.java)
    }


    @JvmStatic
    @MainScope
    @Provides
    fun provideCountryDao(db: AppDatabase): CountryDao {
        return db.getCountryDao()
    }


    @JvmStatic
    @MainScope
    @Provides
    fun provideHomeRepository(
        covidApiMainService: CovidApiMainService,
        countryDao: CountryDao
    ): HomeRepository {
        return HomeRepositoryImpl(covidApiMainService, countryDao)
    }


    @JvmStatic
    @MainScope
    @Provides
    fun provideGlobalRepository(
        covidApiMainService: CovidApiMainService,
        countryDao: CountryDao
    ): GlobalRepository {
        return GlobalRepositoryImpl(covidApiMainService, countryDao)
    }
}