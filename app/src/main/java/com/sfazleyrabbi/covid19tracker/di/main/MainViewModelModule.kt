package com.sfazleyrabbi.covid19tracker.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingwithmitch.openapi.di.main.keys.MainViewModelKey
import com.sfazleyrabbi.covid19tracker.ui.main.global.viewmodel.GlobalViewModel
import com.sfazleyrabbi.covid19tracker.ui.main.home.viewmodel.HomeViewModel
import com.sfazleyrabbi.covid19tracker.viewmodels.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@ExperimentalCoroutinesApi
@FlowPreview
@Module
abstract class MainViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @MainViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(GlobalViewModel::class)
    abstract fun bindGlobalViewModel(globalViewModel: GlobalViewModel): ViewModel
}