package com.mydigipay.challenge.base

import android.app.Application
import com.mydigipay.challenge.presentation.di.githubApiClientModule
import com.mydigipay.challenge.presentation.di.githubApiModule
import com.mydigipay.challenge.presentation.di.viewModels

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(
                githubApiModule,
                githubApiClientModule,
                viewModels
            ))
        }

    }



}