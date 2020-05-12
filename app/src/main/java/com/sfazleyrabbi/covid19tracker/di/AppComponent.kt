package com.sfazleyrabbi.covid19tracker.di

import android.app.Application
import com.sfazleyrabbi.covid19tracker.di.main.MainComponent
import com.sfazleyrabbi.covid19tracker.ui.BaseActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

@Singleton
@Component(
    modules = [
        AppModule::class,
        SubComponentsModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(baseActivity: BaseActivity)

    fun mainComponent(): MainComponent.Factory
}