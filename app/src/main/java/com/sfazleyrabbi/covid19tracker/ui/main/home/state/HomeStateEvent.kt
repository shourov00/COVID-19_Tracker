package com.sfazleyrabbi.covid19tracker.ui.main.home.state

import com.sfazleyrabbi.covid19tracker.util.StateEvent

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */
sealed class HomeStateEvent: StateEvent {

    class CountryDataEvent: HomeStateEvent(){
        override fun errorInfo(): String {
            return "Error finding country data"
        }

        override fun toString(): String {
            return "CountryDataEvent"
        }

    }

}