package com.example.hotels_test

import android.app.Application
import com.example.hotels_test.data.HotelsRemoteSourceImpl
import com.example.hotels_test.data.HotelsRepositoryImpl
import com.example.movies_app.data.core.remote.RetrofitClient
import timber.log.Timber

class HotelsApp : Application() {

    val repository by lazy {
        HotelsRepositoryImpl(
            HotelsRemoteSourceImpl(
                RetrofitClient.moviesApi
            )
        )
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}