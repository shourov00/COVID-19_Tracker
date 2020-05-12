package com.sfazleyrabbi.covid19tracker.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sfazleyrabbi.covid19tracker.models.Country

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@Database(entities = [Country::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCountryDao(): CountryDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}