package es.uva.alumnos.serorca.kphysics

import android.app.Application
import es.uva.alumnos.serorca.kphysics.di.component.AppComponent
import es.uva.alumnos.serorca.kphysics.di.component.DaggerAppComponent
import es.uva.alumnos.serorca.kphysics.di.module.AppModule

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()

        if (BuildConfig.DEBUG) {
            // Maybe TimberPlant etc.
        }
    }

    fun setup() {
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this)).build()
        component.inject(this)
    }

    fun getAppComponent(): AppComponent {
        return component
    }

    companion object {
        lateinit var instance: App private set
    }
}