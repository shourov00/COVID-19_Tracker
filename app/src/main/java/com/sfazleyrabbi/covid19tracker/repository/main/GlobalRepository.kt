package com.sfazleyrabbi.covid19tracker.repository.main

import com.sfazleyrabbi.covid19tracker.di.main.MainScope
import com.sfazleyrabbi.covid19tracker.ui.main.global.state.GlobalViewState
import com.sfazleyrabbi.covid19tracker.ui.main.home.state.HomeViewState
import com.sfazleyrabbi.covid19tracker.util.DataState
import com.sfazleyrabbi.covid19tracker.util.StateEvent
import kotlinx.coroutines.flow.Flow

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@MainScope
interface GlobalRepository {

    fun getAllCountriesInfo(
        query: String,
        stateEvent: StateEvent
    ): Flow<DataState<GlobalViewState>>

}