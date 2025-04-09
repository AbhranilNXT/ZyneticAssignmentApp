package tech.abhranilnxt.zyneticassignmentapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import tech.abhranilnxt.zyneticassignmentapp.di.appModule

class ZyneticAssignmentApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ZyneticAssignmentApp)
            androidLogger()

            modules(appModule)
        }
    }
}