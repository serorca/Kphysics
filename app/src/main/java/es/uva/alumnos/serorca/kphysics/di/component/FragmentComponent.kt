package es.uva.alumnos.serorca.kphysics.di.component

import dagger.Component
import es.uva.alumnos.serorca.kphysics.di.module.FragmentModule
import es.uva.alumnos.serorca.kphysics.ui.experiment.fragment.ExperimentFragment
import es.uva.alumnos.serorca.kphysics.ui.proyectlist.ProyectListFragment
import es.uva.alumnos.serorca.kphysics.ui.sensorlist.SensorListFragment


@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(projectListFragment: ProyectListFragment)

    fun inject(sensorListFragment: SensorListFragment)

    fun inject(experimentFragment: ExperimentFragment)

}