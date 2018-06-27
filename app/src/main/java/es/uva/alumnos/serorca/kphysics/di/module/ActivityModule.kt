package es.uva.alumnos.serorca.kphysics.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import es.uva.alumnos.serorca.kphysics.ui.experiment.activity.ExperimentContract
import es.uva.alumnos.serorca.kphysics.ui.experiment.activity.ExperimentPresenter
import es.uva.alumnos.serorca.kphysics.ui.experiment.fragment.ExperimentFragmentContract
import es.uva.alumnos.serorca.kphysics.ui.experiment.fragment.ExperimentFragmentPresenter
import es.uva.alumnos.serorca.kphysics.ui.main.MainContract
import es.uva.alumnos.serorca.kphysics.ui.main.MainPresenter

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun providePresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideExpPresenter(): ExperimentContract.Presenter {
        return ExperimentPresenter()
    }

    @Provides
    fun provideExpFragmentPresenter(): ExperimentFragmentContract.Presenter{
        return  ExperimentFragmentPresenter()
    }

}