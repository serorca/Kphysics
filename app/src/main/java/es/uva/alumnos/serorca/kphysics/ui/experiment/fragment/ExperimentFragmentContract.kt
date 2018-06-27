package es.uva.alumnos.serorca.kphysics.ui.experiment.fragment

import es.uva.alumnos.serorca.kphysics.ui.base.BaseContract

class ExperimentFragmentContract {

    interface View : BaseContract.View {
        fun showLineGraph()

    }

    interface Presenter : BaseContract.Presenter<View> {

    }
}