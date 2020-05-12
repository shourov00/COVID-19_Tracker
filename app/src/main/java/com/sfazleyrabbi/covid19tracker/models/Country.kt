package com.sfazleyrabbi.covid19tracker.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@Parcelize
@Entity(tableName = "country")
data class Country(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "country")
    @Expose
    var country: String,

    @ColumnInfo(name = "cases")
    @Expose
    var cases: Int,

    @ColumnInfo(name = "deaths")
    @Expose
    var deaths: Int,

    @ColumnInfo(name = "recovered")
    @Expose
    var recovered: Int,

    @ColumnInfo(name = "active")
    @Expose
    var active: Int,

    @ColumnInfo(name = "critical")
    @Expose
    var critical: Int

) : Parcelable {
    override fun toString(): String {
        return " country='$country', cases=$cases, deaths=$deaths, recovered=$recovered, active=$active, critical=$critical)"
    }
}