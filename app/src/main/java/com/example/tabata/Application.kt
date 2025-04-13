package com.example.tabata

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {
    companion object {
        private var sInstance: AppApplication? = null

        val instance: AppApplication
            @Throws(RuntimeException::class)
            get() {
                if (sInstance == null) throw RuntimeException((sInstance as AppApplication).getString(R.string.no_instance_found))
                return sInstance as AppApplication
            }
    }

    override fun onCreate() {
        super.onCreate()
        if (sInstance == null) {
            sInstance = this
        }
    }
}