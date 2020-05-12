package com.sfazleyrabbi.covid19tracker.util

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

abstract class CacheResponseHandler<ViewState, Data>(
    private val response: CacheResult<Data?>, // get result from cache api
    private val stateEvent: StateEvent?
) {
    suspend fun getResult(): DataState<ViewState> {
        return when (response) {
            is CacheResult.GenericError -> {
                DataState.error(
                    response = Response(
                        message = "${stateEvent?.errorInfo()}\n\nReason: ${response.errorMessage}",
                        uiComponentType = UIComponentType.Dialog(),
                        messageType = MessageType.Error()
                    ),
                    stateEvent = stateEvent
                )
            }

            is CacheResult.Success -> {
                if (response.value == null) {
                    DataState.error(
                        response = Response(
                            message = "${stateEvent?.errorInfo()}\n\nReason: Data is NULL.",
                            uiComponentType = UIComponentType.Dialog(),
                            messageType = MessageType.Error()
                        ),
                        stateEvent = stateEvent
                    )
                } else {
                    // else handle the success and pass the result
                    handleSuccess(resultObj = response.value)
                }
            }
        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): DataState<ViewState>
}