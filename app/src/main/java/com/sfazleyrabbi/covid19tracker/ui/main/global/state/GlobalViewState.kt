package com.sfazleyrabbi.covid19tracker.ui.main.global.state

import android.os.Parcelable
import com.sfazleyrabbi.covid19tracker.models.Country
import kotlinx.android.parcel.Parcelize

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

const val GLOBAL_VIEW_STATE_BUNDLE_KEY =
    "com.sfazleyrabbi.covid19tracker.ui.main.global.state.GlobalViewState"

@Parcelize
data class GlobalViewState(
    var globalFields: GlobalFields = GlobalFields()

) : Parcelable {

    @Parcelize
    data class GlobalFields(
        var countryList: List<Country>? = null,
        var searchQuery: String? = null,
        var layoutManagerState: Parcelable? = null // save layoutManager State
    ) : Parcelable
}