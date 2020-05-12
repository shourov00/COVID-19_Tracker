package com.sfazleyrabbi.covid19tracker.repository.main

import android.util.Log
import com.sfazleyrabbi.covid19tracker.api.main.CovidApiMainService
import com.sfazleyrabbi.covid19tracker.api.main.responses.CountryResponse
import com.sfazleyrabbi.covid19tracker.models.Country
import com.sfazleyrabbi.covid19tracker.persistence.CountryDao
import com.sfazleyrabbi.covid19tracker.repository.NetworkBoundResource
import com.sfazleyrabbi.covid19tracker.ui.main.global.state.GlobalViewState
import com.sfazleyrabbi.covid19tracker.util.DataState
import com.sfazleyrabbi.covid19tracker.util.StateEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@FlowPreview
class GlobalRepositoryImpl
@Inject
constructor(
    val covidApiMainService: CovidApiMainService,
    val countryDao: CountryDao
) : GlobalRepository {
    private val TAG: String = "AppDebug"

    override fun getAllCountriesInfo(
        query: String,
        stateEvent: StateEvent
    ): Flow<DataState<GlobalViewState>> {
        return object : NetworkBoundResource<List<CountryResponse>, List<Country>, GlobalViewState>(
            dispatcher = IO,
            stateEvent = stateEvent,
            apiCall = {
                covidApiMainService.searchListCountries()
            },
            cacheCall = {
                countryDao.getAllCountiesInfo(query)
            }
        ) {
            override suspend fun updateCache(networkObject: List<CountryResponse>) {
                val countryList: ArrayList<Country> = ArrayList()
                for (countryResponse in networkObject) {
                    countryList.add(countryResponse.toCountry())
                }
                withContext(IO) {
                    for (country in countryList) {
                        try {
                            launch {
                                countryDao.insertAndReplace(country)
                            }
                        } catch (e: Exception) {
                            // nothing to do here
                        }
                    }
                }
            }

            override fun handleCacheSuccess(resultObj: List<Country>): DataState<GlobalViewState> {
                val viewState = GlobalViewState(
                    globalFields = GlobalViewState.GlobalFields(
                        countryList = resultObj
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