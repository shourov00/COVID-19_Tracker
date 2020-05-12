package com.sfazleyrabbi.covid19tracker.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sfazleyrabbi.covid19tracker.models.Country

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndReplace(country: Country): Long

    @Query("SELECT * FROM country WHERE country =:countryName")
    suspend fun searchByCountryName(countryName: String): Country

    @Query("SELECT * FROM country WHERE country LIKE '%' || :query || '%'")
    suspend fun getAllCountiesInfo(query: String): List<Country>

}