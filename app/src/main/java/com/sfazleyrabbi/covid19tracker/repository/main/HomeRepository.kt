package com.sfazleyrabbi.covid19tracker.repository.main

import com.sfazleyrabbi.covid19tracker.di.main.MainScope
import com.sfazleyrabbi.covid19tracker.ui.main.home.state.HomeViewState
import com.sfazleyrabbi.covid19tracker.util.DataState
import com.sfazleyrabbi.covid19tracker.util.StateEvent
import kotlinx.coroutines.flow.Flow


/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@MainScope
interface HomeRepository {

    fun getCountryInfo(
        countryName: String,
        stateEvent: StateEvent
    ): Flow<DataState<HomeViewState>>
}