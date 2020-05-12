package com.sfazleyrabbi.covid19tracker.ui.main.global.viewmodel

import com.sfazleyrabbi.covid19tracker.di.main.MainScope
import com.sfazleyrabbi.covid19tracker.repository.main.GlobalRepositoryImpl
import com.sfazleyrabbi.covid19tracker.ui.BaseViewModel
import com.sfazleyrabbi.covid19tracker.ui.main.global.state.GlobalStateEvent
import com.sfazleyrabbi.covid19tracker.ui.main.global.state.GlobalViewState
import com.sfazleyrabbi.covid19tracker.util.*
import com.sfazleyrabbi.covid19tracker.util.ERROR_HANDLING.Companion.INVALID_STATE_EVENT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@FlowPreview
@ExperimentalCoroutinesApi
@MainScope
class GlobalViewModel
@Inject
constructor(
    private val globalRepository: GlobalRepositoryImpl
) : BaseViewModel<GlobalViewState>() {

    override fun handleNewData(data: GlobalViewState) {
        data.globalFields.let { globalFields ->
            globalFields.countryList?.let { countryList ->
                setCountryListData(countryList)
            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        if (!isJobAlreadyActive(stateEvent)) {
            val job: Flow<DataState<GlobalViewState>> = when (stateEvent) {
                is GlobalStateEvent.CountrySearchEvent -> {
                    if (stateEvent.clearLayoutManagerState) {
                        clearLayoutManagerState()
                    }
                    globalRepository.getAllCountriesInfo(
                        query = getSearchQuery(),
                        stateEvent = stateEvent
                    )
                }

                else -> {
                    flow {
                        emit(
                            DataState.error<GlobalViewState>(
                                response = Response(
                                    message = INVALID_STATE_EVENT,
                                    uiComponentType = UIComponentType.None(),
                                    messageType = MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        )
                    }
                }
            }
            launchJob(stateEvent, job)
        }
    }

    override fun initNewViewState(): GlobalViewState {
        return GlobalViewState()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}