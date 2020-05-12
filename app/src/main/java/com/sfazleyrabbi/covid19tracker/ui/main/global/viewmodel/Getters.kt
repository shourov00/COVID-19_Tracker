package com.sfazleyrabbi.covid19tracker.ui.main.global.viewmodel

import com.sfazleyrabbi.covid19tracker.ui.main.global.state.GlobalStateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@ExperimentalCoroutinesApi
@FlowPreview
fun GlobalViewModel.getSearchQuery(): String {
    return getCurrentViewStateOrNew().globalFields.searchQuery ?: return ""
}

@ExperimentalCoroutinesApi
@FlowPreview
fun GlobalViewModel.refreshFromCache(){
    if(!isJobAlreadyActive(GlobalStateEvent.CountrySearchEvent())){
        setStateEvent(GlobalStateEvent.CountrySearchEvent(false))
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
fun GlobalViewModel.loadInitialList(){
    if(!isJobAlreadyActive(GlobalStateEvent.CountrySearchEvent())){
        setStateEvent(GlobalStateEvent.CountrySearchEvent())
    }
}
