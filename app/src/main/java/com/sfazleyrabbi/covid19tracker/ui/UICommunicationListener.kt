package com.sfazleyrabbi.covid19tracker.ui

import com.sfazleyrabbi.covid19tracker.util.Response
import com.sfazleyrabbi.covid19tracker.util.StateMessageCallback

/**
 * Created by Fazley Rabbi on 22 April 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

interface UICommunicationListener {

    // a dialog will show
    // remove message from stack
    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )

    fun displayProgressBar(isLoading: Boolean)

    fun expandAppBar()

    fun hideSoftKeyboard()

    fun isPhoneCallPermissionGranted(): Boolean

}