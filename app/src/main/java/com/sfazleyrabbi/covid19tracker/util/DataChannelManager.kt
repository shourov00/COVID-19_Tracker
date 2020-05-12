package com.sfazleyrabbi.covid19tracker.util

import android.service.autofill.Dataset
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

// When the state events get fired of its gonna create a flow and add that flow to channel
// and single stream of data returns that the data ui observe
// it can handle any amount of state event with channel

@ExperimentalCoroutinesApi
abstract class DataChannelManager<ViewState> {

    private val TAG: String = "AppDebug"

    private val _activeStateEvents: HashSet<String> = HashSet()
    private val _numActiveJobs: MutableLiveData<Int> = MutableLiveData()
    private val dataChannel: ConflatedBroadcastChannel<DataState<ViewState>> =
        ConflatedBroadcastChannel()
    private var channelScope: CoroutineScope? = null
    val messageStack = MessageStack()

    val numActiveJobs: LiveData<Int>
        get() = _numActiveJobs

    init {
        dataChannel.asFlow()
            .onEach { dataState ->
                dataState.data?.let { data ->
                    handleNewData(data)
                    removeStateEvent(dataState.stateEvent)
                }
                dataState.stateMessage?.let { stateMessage ->
                    handleNewStateMessage(stateMessage)
                    removeStateEvent(dataState.stateEvent)
                }
            }.launchIn(CoroutineScope(Main))
    }

    // as soon as new view model comes into view init new channel
    fun setUpChannel() {
        cancelJobs()
        setupNewChannelScope(CoroutineScope(IO))
    }

    fun launchJob(
        stateEvent: StateEvent,
        jobFunction: Flow<DataState<ViewState>>
    ) {
        if (!isStateEventActive(stateEvent) && messageStack.size == 0) {
            addStateEvent(stateEvent)
            jobFunction.onEach { dataState ->
                offerToDataChannel(dataState)
            }.launchIn(getChannelScope())
        }
    }

    private fun offerToDataChannel(dataState: DataState<ViewState>) {
        dataChannel.let {
            if (!it.isClosedForSend) {
                it.offer(dataState)
            }
        }
    }

    fun clearStateMessage(index: Int = 0) {
        messageStack.removeAt(index)
    }

    private fun addStateEvent(stateEvent: StateEvent) {
        _activeStateEvents.add(stateEvent.toString())
        syncNumActiveStateEvents()
    }

    private fun removeStateEvent(stateEvent: StateEvent?) {
        _activeStateEvents.remove(stateEvent.toString())
        syncNumActiveStateEvents()
    }

    fun isJobAlreadyActive(stateEvent: StateEvent): Boolean {
        return isStateEventActive(stateEvent)
    }

    private fun isStateEventActive(stateEvent: StateEvent): Boolean {
        return _activeStateEvents.contains(stateEvent.toString())
    }

    private fun handleNewStateMessage(stateMessage: StateMessage) {
        appendStateMessage(stateMessage)
    }

    private fun appendStateMessage(stateMessage: StateMessage) {
        messageStack.add(stateMessage)
    }

    private fun setupNewChannelScope(coroutineScope: CoroutineScope): CoroutineScope {
        channelScope = coroutineScope
        return channelScope as CoroutineScope
    }

    private fun getChannelScope(): CoroutineScope {
        return channelScope ?: setupNewChannelScope(CoroutineScope(IO))
    }

    fun cancelJobs() {
        if (channelScope != null) {
            if (channelScope?.isActive == true) {
                channelScope?.cancel()
            }
            channelScope = null
        }

        clearActiveStateEventCounter()
    }

    private fun clearActiveStateEventCounter() {
        _activeStateEvents.clear()
        syncNumActiveStateEvents()
    }

    private fun syncNumActiveStateEvents() {
        _numActiveJobs.value = _activeStateEvents.size
    }

    abstract fun handleNewData(data: ViewState)
}