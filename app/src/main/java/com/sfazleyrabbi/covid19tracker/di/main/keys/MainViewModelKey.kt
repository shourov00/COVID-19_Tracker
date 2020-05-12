package com.codingwithmitch.openapi.di.main.keys


/**
 * Created by Fazley Rabbi on 26 April 2020
 * Copyright (c) 2020 www.fazleyrabbi.net All rights reserved.
 */

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class MainViewModelKey(val value: KClass<out ViewModel>)