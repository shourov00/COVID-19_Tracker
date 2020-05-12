package com.sfazleyrabbi.covid19tracker.api.main.responses

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sfazleyrabbi.covid19tracker.models.Country

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

class CountryResponse (
    @SerializedName("country")
    @Expose
    var country: String,

    @SerializedName("cases")
    @Expose
    var cases: Int,

    @SerializedName("deaths")
    @Expose
    var deaths: Int,

    @SerializedName("recovered")
    @Expose
    var recovered: Int,

    @SerializedName("active")
    @Expose
    var active: Int,

    @SerializedName("critical")
    @Expose
    var critical: Int
){
    fun toCountry(): Country{
        return Country(
            country, cases, deaths, recovered, active, critical
        )
    }

    override fun toString(): String {
        return "CountryResponse(country='$country', cases=$cases, deaths=$deaths, recovered=$recovered, active=$active, critical=$critical)"
    }

}