package com.sfazleyrabbi.covid19tracker.ui.main.global.state

import com.sfazleyrabbi.covid19tracker.util.StateEvent

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

sealed class GlobalStateEvent: StateEvent {

    class CountrySearchEvent(
        val clearLayoutManagerState: Boolean = true
    ): GlobalStateEvent(){
        override fun errorInfo(): String {
            return "Error searching for country covid-19 info"
        }

        override fun toString(): String {
            return "CountrySearchEvent"
        }
    }

    class None: GlobalStateEvent() {
        override fun errorInfo(): String {
            return "None."
        }
    }

}