package com.sfazleyrabbi.covid19tracker.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.android.parcel.IgnoredOnParcel
import java.lang.IndexOutOfBoundsException
import java.sql.Statement

/**
 * Created by Fazley Rabbi on 26 April 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

/*
* Array list of state messages that handle all responses from repository
* as soon as if there is a message that's return from the repository to all
* the way back UI, its gonna get added to this message stack
* first index of the message stack will show on UI
* when user sees a message or press okay, remove first index message from stack
* and 2nd index message will show (if any)
*/
class MessageStack : ArrayList<StateMessage>() {
    private val TAG: String = "AppDebug"

    // this mutableLiveData will be observe on ui
    @IgnoredOnParcel // not be store on parcel
    private val _stateMessage: MutableLiveData<StateMessage?> = MutableLiveData()

    // return _stateMessage
    @IgnoredOnParcel
    val stateMessage: LiveData<StateMessage?>
        get() = _stateMessage

    override fun addAll(elements: Collection<StateMessage>): Boolean {
        // loop through all the elements
        for (element in elements) {
            add(element) // add them to stack
        }
        return true // always return true. we don't care about result bool.
    }

    override fun add(element: StateMessage): Boolean {
        if (this.contains(element)) { // prevent duplicate errors added to stack
            return false
        }
        val transaction = super.add(element) // add is called
        if(this.size == 1){
            // if list size = 1 means its the first message then set message to mutableLiveData
            setStateMessage(stateMessage = element)
        }
        return transaction
    }

    // when we want to remove a message or list is empty
    override fun removeAt(index: Int): StateMessage {
        try {
            val transaction = super.removeAt(index)
            if (this.size > 0) {
                // if array size is greater then 0 set first index to mutableLiveData
                setStateMessage(stateMessage = this[0])
            } else {
                // if array is empty set null to mutableLiveData
                setStateMessage(null)
            }
            return transaction
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }

        return StateMessage(
            Response(
                message = "does nothing",
                uiComponentType = UIComponentType.None(),
                messageType = MessageType.None()
            )
        )
    }

    private fun setStateMessage(stateMessage: StateMessage?){
        _stateMessage.value = stateMessage
    }
}