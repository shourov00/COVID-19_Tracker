package com.sfazleyrabbi.covid19tracker.ui

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import com.sfazleyrabbi.covid19tracker.util.StateMessageCallback

/**
 * Created by Fazley Rabbi on 10 April 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

//If we passing a string from stringResources

fun Activity.displayToast(
    message:String,
    stateMessageCallback: StateMessageCallback
){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    stateMessageCallback.removeMessageFromStack()
}


interface AreYouSureCallback {

    fun proceed()

    fun cancel()
}

