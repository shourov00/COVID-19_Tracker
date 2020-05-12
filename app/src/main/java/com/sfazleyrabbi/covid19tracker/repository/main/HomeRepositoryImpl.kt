package com.sfazleyrabbi.covid19tracker.repository.main

import android.util.Log
import com.sfazleyrabbi.covid19tracker.api.main.CovidApiMainService
import com.sfazleyrabbi.covid19tracker.api.main.responses.CountryResponse
import com.sfazleyrabbi.covid19tracker.models.Country
import com.sfazleyrabbi.covid19tracker.persistence.CountryDao
import com.sfazleyrabbi.covid19tracker.repository.NetworkBoundResource
import com.sfazleyrabbi.covid19tracker.ui.main.home.state.HomeViewState
import com.sfazleyrabbi.covid19tracker.util.DataState
import com.sfazleyrabbi.covid19tracker.util.StateEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@FlowPreview
class HomeRepositoryImpl
@Inject
constructor(
    val covidApiMainService: CovidApiMainService,
    val countryDao: CountryDao
) : HomeRepository {

    // Network and Cache (GET)Request -> NETWORK BOUND RESOURCE
    override fun getCountryInfo(
        countryName: String,
        stateEvent: StateEvent
    ): Flow<DataState<HomeViewState>> {
        return object : NetworkBoundResource<CountryResponse, Country, HomeViewState>(
            dispatcher = IO,
            stateEvent = stateEvent,
            apiCall = { covidApiMainService.searchByCountry(countryName) },
            cacheCall = { countryDao.searchByCountryName(countryName) }
        ) {
            override suspend fun updateCache(networkObject: CountryResponse) {
                // fetched data successfully now update the local database
                val countryData = networkObject.toCountry()
                withContext(IO) {
                    try {
                        // Launch each insert as a separate job to be executed in parallel
                        // create a new coroutine for each insertion
                        launch {
                            // insert country data to db
                            countryDao.insertAndReplace(countryData)
                        }
                    } catch (e: Exception) {
                        // Could send an error report here or something but I don't think you should throw an error to the UI
                        // Since there could be many country data being inserted/updated.
                    }
                }
            }

            override fun handleCacheSuccess(resultObj: Country): DataState<HomeViewState> {
                // after successfully save to database
                val viewState = HomeViewState(
                    homeFields = HomeViewState.HomeFields(
                        country = resultObj
                    )
                )

                return DataState.data(
                    response = null,
                    data = viewState,
                    stateEvent = stateEvent
                )
            }
        }.result
    }
}