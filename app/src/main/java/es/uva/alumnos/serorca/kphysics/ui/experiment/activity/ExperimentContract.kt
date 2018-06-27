package es.uva.alumnos.serorca.kphysics.ui.experiment.activity

import es.uva.alumnos.serorca.kphysics.ui.base.BaseContract

class ExperimentContract {

    interface View : BaseContract.View {
        fun showExperimentFragment()
    }

    interface Presenter : BaseContract.Presenter<View> {}
}