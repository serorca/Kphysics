package es.uva.alumnos.serorca.kphysics.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import es.uva.alumnos.serorca.kphysics.App
import es.uva.alumnos.serorca.kphysics.di.scope.PerApplication
import javax.inject.Singleton

@Module
class AppModule(private val baseApp: App) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return baseApp
    }
}