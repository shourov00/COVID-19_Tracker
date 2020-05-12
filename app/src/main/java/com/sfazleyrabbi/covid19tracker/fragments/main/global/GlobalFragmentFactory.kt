package com.sfazleyrabbi.covid19tracker.fragments.main.global

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.sfazleyrabbi.covid19tracker.di.main.MainScope
import com.sfazleyrabbi.covid19tracker.ui.main.global.GlobalFragment
import com.sfazleyrabbi.covid19tracker.ui.main.home.HomeFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@FlowPreview
@MainScope
class GlobalFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            GlobalFragment::class.java.name -> {
                GlobalFragment(viewModelFactory)
            }

            else -> {
                GlobalFragment(viewModelFactory)
            }
        }
}