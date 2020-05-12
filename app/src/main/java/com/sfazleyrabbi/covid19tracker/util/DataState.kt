package com.sfazleyrabbi.covid19tracker.util

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

data class DataState<T>(
    var stateMessage: StateMessage? = null,
    var data: T? = null,
    var stateEvent: StateEvent? = null
) {
    companion object {
        fun <T> error(response: Response, stateEvent: StateEvent?): DataState<T> {
            return DataState(
                stateMessage = StateMessage(response),
                data = null,
                stateEvent = stateEvent
            )
        }

        fun <T> data(response: Response?, data: T? = null, stateEvent: StateEvent?): DataState<T> {
            return DataState(
                stateMessage = response?.let { StateMessage(it) },
                data = data,
                stateEvent = stateEvent
            )
        }
    }
}