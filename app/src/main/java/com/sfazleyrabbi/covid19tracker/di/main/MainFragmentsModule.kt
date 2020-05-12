package com.sfazleyrabbi.covid19tracker.di.main

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigator
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.sfazleyrabbi.covid19tracker.fragments.main.global.GlobalFragmentFactory
import com.sfazleyrabbi.covid19tracker.fragments.main.home.HomeFragmentFactory
import com.sfazleyrabbi.covid19tracker.fragments.main.info.InfoFragmentFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.FlowPreview
import javax.inject.Named

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@FlowPreview
@Module
object MainFragmentsModule {

    @JvmStatic
    @MainScope
    @Provides
    @Named("HomeFragmentFactory")
    fun provideHomeFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory
    ): FragmentFactory {
        return HomeFragmentFactory(
            viewModelFactory
        )
    }

    @JvmStatic
    @MainScope
    @Provides
    @Named("GlobalFragmentFactory")
    fun provideGlobalFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory
    ): FragmentFactory {
        return GlobalFragmentFactory(
            viewModelFactory
        )
    }

    @JvmStatic
    @MainScope
    @Provides
    @Named("InfoFragmentFactory")
    fun provideInfoFragmentFactory(
        requestOptions: RequestOptions,
        requestManager: RequestManager
    ): FragmentFactory {
        return InfoFragmentFactory(requestOptions, requestManager)
    }

}