package com.raul.androidapps.softwaretestrevolut.di.components

import com.raul.androidapps.softwaretestrevolut.RevolutApplication


object ComponentFactory {

    fun component(context: RevolutApplication): RevolutComponent {
        return DaggerRevolutComponent.builder()
                .application(context)
                .build()
    }

}