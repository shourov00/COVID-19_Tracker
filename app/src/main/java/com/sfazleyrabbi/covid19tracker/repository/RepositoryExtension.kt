package com.sfazleyrabbi.covid19tracker.repository

import com.sfazleyrabbi.covid19tracker.util.*
import com.sfazleyrabbi.covid19tracker.util.ApiResult.*
import com.sfazleyrabbi.covid19tracker.util.Constants.Companion.CACHE_TIMEOUT
import com.sfazleyrabbi.covid19tracker.util.Constants.Companion.NETWORK_TIMEOUT
import com.sfazleyrabbi.covid19tracker.util.ERROR_HANDLING.Companion.CACHE_ERROR_TIMEOUT
import com.sfazleyrabbi.covid19tracker.util.ERROR_HANDLING.Companion.NETWORK_ERROR_TIMEOUT
import com.sfazleyrabbi.covid19tracker.util.ERROR_HANDLING.Companion.UNKNOWN_ERROR
import com.sfazleyrabbi.covid19tracker.util.MessageType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

// This functions will be use any repository to handle error and invoke suspending functions
suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T? // request from retrofit
): ApiResult<T?> {
    return withContext(dispatcher) {
        try {
            withTimeout(NETWORK_TIMEOUT) {
                Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()

            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408
                    GenericError(code, NETWORK_ERROR_TIMEOUT)
                }

                is IOException -> {
                    NetworkError
                }

                // if actual error from the network then convert error body to string and pass response
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    GenericError(
                        null,
                        UNKNOWN_ERROR
                    )
                }
            }

        }
    }
}

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T? // request from room
): CacheResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(CACHE_TIMEOUT) {
                CacheResult.Success(cacheCall.invoke()) //invoke the function
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else -> {
                    CacheResult.GenericError(UNKNOWN_ERROR)
                }
            }
        }
    }
}

fun <ViewState> buildError(
    message: String,
    uiComponentType: UIComponentType,
    stateEvent: StateEvent?
): DataState<ViewState> {
    return DataState.error(
        response = Response(
            message = "${stateEvent?.errorInfo()}\n\nReason: ${message}",
            uiComponentType = uiComponentType,
            messageType = MessageType.Error()
        ),
        stateEvent = stateEvent
    )

}

fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (e: Exception) {
        UNKNOWN_ERROR
    }
}
