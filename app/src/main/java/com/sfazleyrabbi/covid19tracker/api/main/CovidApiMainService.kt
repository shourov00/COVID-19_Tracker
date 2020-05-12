package com.sfazleyrabbi.covid19tracker.api.main

import com.sfazleyrabbi.covid19tracker.api.main.responses.CountryResponse
import com.sfazleyrabbi.covid19tracker.models.Country
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

interface CovidApiMainService {

    @GET("countries/{country_name}")
    suspend fun searchByCountry(
        @Path("country_name") countryName: String
    ): CountryResponse

    @GET("countries/")
    suspend fun searchListCountries(): List<CountryResponse>
}