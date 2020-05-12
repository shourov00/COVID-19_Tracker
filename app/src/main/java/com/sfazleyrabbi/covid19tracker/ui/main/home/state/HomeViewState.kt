package com.sfazleyrabbi.covid19tracker.ui.main.home.state

import android.os.Parcelable
import com.sfazleyrabbi.covid19tracker.models.Country
import kotlinx.android.parcel.Parcelize

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */
const val HOME_VIEW_STATE_BUNDLE_KEY =
    "com.sfazleyrabbi.covid19tracker.ui.main.home.state.HomeViewState"

@Parcelize
data class HomeViewState(

    // get the single country data
    var homeFields: HomeFields = HomeFields()

): Parcelable{

    @Parcelize
    data class HomeFields(
        var country: Country? = null
    ): Parcelable

}