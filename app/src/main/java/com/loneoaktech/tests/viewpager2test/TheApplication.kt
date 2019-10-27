package com.loneoaktech.tests.viewpager2test

import android.app.Application
import timber.log.Timber

@Suppress("unused") // It's in the manifest
class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}