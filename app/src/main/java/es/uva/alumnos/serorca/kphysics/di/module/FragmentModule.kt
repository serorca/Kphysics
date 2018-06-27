package es.uva.alumnos.serorca.kphysics.di.module

import dagger.Module
import dagger.Provides
import es.uva.alumnos.serorca.kphysics.api.ApiServiceInterface
import es.uva.alumnos.serorca.kphysics.ui.experiment.fragment.ExperimentFragmentContract
import es.uva.alumnos.serorca.kphysics.ui.experiment.fragment.ExperimentFragmentPresenter
import es.uva.alumnos.serorca.kphysics.ui.proyectlist.ProyectListContract
import es.uva.alumnos.serorca.kphysics.ui.proyectlist.ProyectListPresenter
import es.uva.alumnos.serorca.kphysics.ui.sensorlist.SensorContract
import es.uva.alumnos.serorca.kphysics.ui.sensorlist.SensorPresenter

@Module
class FragmentModule {

    @Provides
    fun provideAboutPresenter(): ProyectListContract.Presenter {
        return ProyectListPresenter()
    }

    @Provides
    fun provideListPresenter(): SensorContract.Presenter {
        return SensorPresenter()
    }

    @Provides
    fun provideExpFragmentPresenter(): ExperimentFragmentContract.Presenter{
        return ExperimentFragmentPresenter()
    }

    @Provides
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }
}