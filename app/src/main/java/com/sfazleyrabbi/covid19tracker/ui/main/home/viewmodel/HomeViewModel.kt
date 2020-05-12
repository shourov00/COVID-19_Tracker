package com.sfazleyrabbi.covid19tracker.ui.main.home.viewmodel

import com.sfazleyrabbi.covid19tracker.di.main.MainScope
import com.sfazleyrabbi.covid19tracker.repository.main.HomeRepositoryImpl
import com.sfazleyrabbi.covid19tracker.ui.BaseViewModel
import com.sfazleyrabbi.covid19tracker.ui.main.home.state.HomeStateEvent
import com.sfazleyrabbi.covid19tracker.ui.main.home.state.HomeViewState
import com.sfazleyrabbi.covid19tracker.util.*
import com.sfazleyrabbi.covid19tracker.util.ERROR_HANDLING.Companion.INVALID_STATE_EVENT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@FlowPreview
@ExperimentalCoroutinesApi
@MainScope
class HomeViewModel
@Inject
constructor(
    private val homeRepository: HomeRepositoryImpl
) : BaseViewModel<HomeViewState>() {

    override fun handleNewData(data: HomeViewState) {
        // get the data from channel
        data.homeFields.let { homeFields ->
            homeFields.country?.let { country ->
                setCountry(country)
            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        // handle the state event calls
        if (!isJobAlreadyActive(stateEvent)) {
            val job: Flow<DataState<HomeViewState>> = when (stateEvent) {
                is HomeStateEvent.CountryDataEvent -> {
                    homeRepository.getCountryInfo(
                        countryName = getCountryName(),
                        stateEvent = stateEvent
                    )
                }
                else -> {
                    flow {
                        emit(
                            DataState.error<HomeViewState>(
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

    override fun initNewViewState(): HomeViewState {
        return HomeViewState()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}