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
fun HomeViewModel.getCountryData(): Country {
    getCurrentViewStateOrNew().let {
        return it.homeFields.country?.let {
            return it
        } ?: getDummyCountry()
    }
}


@FlowPreview
@ExperimentalCoroutinesApi
fun HomeViewModel.getCountryName(): String {
    getCurrentViewStateOrNew().let {
        it.homeFields.country?.let {
            return it.country
        }
    }
    return "Bangladesh"
}


@ExperimentalCoroutinesApi
@FlowPreview
fun HomeViewModel.getDummyCountry(): Country {
    return Country("", 0, 0, 0, 0, 0)
}