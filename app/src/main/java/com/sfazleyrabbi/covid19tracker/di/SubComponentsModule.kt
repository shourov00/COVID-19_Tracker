package com.sfazleyrabbi.covid19tracker.di

import com.sfazleyrabbi.covid19tracker.di.main.MainComponent
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@FlowPreview
@ExperimentalCoroutinesApi
@Module(
    subcomponents = [
        MainComponent::class
    ]
)
class SubComponentsModule