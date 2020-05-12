package com.sfazleyrabbi.covid19tracker.util

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

sealed class ApiResult<out T> {

    data class Success<out T>(val value: T) : ApiResult<T>()

    data class GenericError(
        val code: Int? = null,
        val errorMessage: String? = null
    ): ApiResult<Nothing>()

    object NetworkError: ApiResult<Nothing>()
}