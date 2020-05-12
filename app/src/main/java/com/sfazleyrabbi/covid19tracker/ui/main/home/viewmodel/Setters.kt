package com.sfazleyrabbi.covid19tracker.ui.main.home.viewmodel

import com.sfazleyrabbi.covid19tracker.models.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@ExperimentalCoroutinesApi
@FlowPreview
fun HomeViewModel.setCountry(country: Country){
    // update viewState after getting data from api
    val update = getCurrentViewStateOrNew()
    update.homeFields.country = country
    setViewState(update)
}