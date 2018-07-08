package es.uva.alumnos.serorca.kphysics.ui.sensorlist

import es.uva.alumnos.serorca.kphysics.data.model.SensorInfo
import es.uva.alumnos.serorca.kphysics.ui.base.BaseContract

class SensorContract {

    interface View: BaseContract.View{
        fun loadListSucces(list: ArrayList<SensorInfo>)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun loadList()
    }
}