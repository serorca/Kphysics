package es.uva.alumnos.serorca.kphysics.di.component

import dagger.Component
import es.uva.alumnos.serorca.kphysics.di.module.ActivityModule
import es.uva.alumnos.serorca.kphysics.ui.experiment.activity.ExperimentActivity
import es.uva.alumnos.serorca.kphysics.ui.main.MainActivity

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(experimentActivity: ExperimentActivity)

}