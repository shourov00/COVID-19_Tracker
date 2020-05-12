package com.sfazleyrabbi.covid19tracker.util

import com.sfazleyrabbi.covid19tracker.ui.AreYouSureCallback


/**
 * Created by Fazley Rabbi on 03 April 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */


data class StateMessage(val response: Response)

// From the repository a Response can get the message and also the uiComponent and message type
data class Response(
    val message: String?,
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)

// How you want to show the error?
sealed class UIComponentType {

    class Toast : UIComponentType()

    class Dialog : UIComponentType()

    class AreYouSureDialog(
        val callback: AreYouSureCallback
    ) : UIComponentType()

    class None : UIComponentType()
}

sealed class MessageType{
    class Success: MessageType()

    class Error: MessageType()

    class Info: MessageType()

    class None: MessageType()
}

// Get message from repository to messageStack
// How we remove state messages from the stack when the user press okay.
interface StateMessageCallback{
    fun removeMessageFromStack()
}