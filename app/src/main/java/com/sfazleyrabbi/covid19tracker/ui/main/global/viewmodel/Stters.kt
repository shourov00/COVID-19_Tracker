package com.sfazleyrabbi.covid19tracker.ui.main.global.viewmodel

import android.os.Parcelable
import com.sfazleyrabbi.covid19tracker.models.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@ExperimentalCoroutinesApi
@FlowPreview
fun GlobalViewModel.setCountryListData(countryList: List<Country>) {
    val update = getCurrentViewStateOrNew()
    update.globalFields.countryList = countryList
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
fun GlobalViewModel.setQuery(query: String) {
    val update = getCurrentViewStateOrNew()
    update.globalFields.searchQuery = query
    setViewState(update)
}

@ExperimentalCoroutinesApi
@FlowPreview
fun GlobalViewModel.clearLayoutManagerState() {
    val update = getCurrentViewStateOrNew()
    update.globalFields.layoutManagerState = null
    setViewState(update)
}

@ExperimentalCoroutinesApi
@FlowPreview
fun GlobalViewModel.setLayoutManagerState(layoutManagerState: Parcelable){
    val update = getCurrentViewStateOrNew()
    update.globalFields.layoutManagerState = layoutManagerState
    setViewState(update)
}
