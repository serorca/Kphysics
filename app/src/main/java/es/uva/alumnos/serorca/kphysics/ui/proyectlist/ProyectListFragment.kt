package es.uva.alumnos.serorca.kphysics.ui.proyectlist

import android.support.v4.app.Fragment

class ProyectListFragment: Fragment(), ProyectListContract.View {

    companion object {
        val TAG: String = "ProyectListFragment"

        fun newInstance() = ProyectListFragment()
    }
}