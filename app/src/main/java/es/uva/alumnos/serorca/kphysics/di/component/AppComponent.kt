package es.uva.alumnos.serorca.kphysics.di.component

import dagger.Component
import es.uva.alumnos.serorca.kphysics.App
import es.uva.alumnos.serorca.kphysics.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(application: App)

}