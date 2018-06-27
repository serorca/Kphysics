package es.uva.alumnos.serorca.kphysics.ui.main

import es.uva.alumnos.serorca.kphysics.ui.base.BaseContract

class MainContract {

    interface View: BaseContract.View {
        fun showProyectFragment()
        fun showSensorFragment()
    }

    interface Presenter: BaseContract.Presenter<MainContract.View> {
        fun onProyectBtnClick()
    }
}