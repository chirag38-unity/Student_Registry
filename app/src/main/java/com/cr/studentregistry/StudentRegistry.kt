package com.cr.studentregistry

import android.app.Application
import com.cr.studentregistry.di.appModule
import com.cr.studentregistry.di.networkModule
import com.cr.studentregistry.di.repositoryModule
import com.cr.studentregistry.di.viewModelModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class StudentRegistry : Application() {

    override fun onCreate() {
        Napier.base(DebugAntilog())
        super.onCreate()

        startKoin {
            androidContext(this@StudentRegistry)
            modules(
                appModule,
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }

    }

}