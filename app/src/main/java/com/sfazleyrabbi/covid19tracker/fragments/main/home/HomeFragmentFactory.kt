package com.sfazleyrabbi.covid19tracker.fragments.main.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.sfazleyrabbi.covid19tracker.di.main.MainScope
import com.sfazleyrabbi.covid19tracker.ui.main.home.HomeFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@MainScope
class HomeFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            HomeFragment::class.java.name -> {
                HomeFragment(viewModelFactory)
            }

            else -> {
                HomeFragment(viewModelFactory)
            }
        }
}