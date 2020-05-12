package com.sfazleyrabbi.covid19tracker.fragments.main.info

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.sfazleyrabbi.covid19tracker.di.main.MainScope
import com.sfazleyrabbi.covid19tracker.ui.main.global.GlobalFragment
import com.sfazleyrabbi.covid19tracker.ui.main.info.InfoFragment
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

/**
 * Created by Fazley Rabbi on 12 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@FlowPreview
@MainScope
class InfoFragmentFactory
@Inject
constructor(
    private val requestOptions: RequestOptions,
    private val requestManager: RequestManager
) : FragmentFactory() {

    @FlowPreview
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            InfoFragment::class.java.name -> {
                InfoFragment(requestOptions, requestManager)
            }

            else -> {
                InfoFragment(requestOptions, requestManager)
            }
        }
}