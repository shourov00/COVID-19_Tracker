package com.sfazleyrabbi.covid19tracker

import android.app.Application
import com.sfazleyrabbi.covid19tracker.di.AppComponent
import com.sfazleyrabbi.covid19tracker.di.DaggerAppComponent
import com.sfazleyrabbi.covid19tracker.di.main.MainComponent

/**
 * Created by Fazley Rabbi on 11 May 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

class BaseApplication : Application(){

    lateinit var appComponent: AppComponent

    private var mainComponent: MainComponent? = null

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    fun mainComponent(): MainComponent{
        if(mainComponent == null){
            mainComponent = appComponent.mainComponent().create()
        }

        return mainComponent as MainComponent
    }

    fun releaseMainComponent(){
        mainComponent = null
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .application(this).build()
    }

}