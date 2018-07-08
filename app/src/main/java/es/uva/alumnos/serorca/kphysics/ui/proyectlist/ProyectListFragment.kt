package es.uva.alumnos.serorca.kphysics.ui.proyectlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.uva.alumnos.serorca.kphysics.R
import kotlinx.android.synthetic.main.fragment_project_list.*

class ProyectListFragment: Fragment(), ProyectListContract.View {

    companion object {

        const val TAG: String = "ProyectListFragment"
        fun newInstance() = ProyectListFragment()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProyectListAdapter(context!!)

        recyclerGridView.layoutManager = GridLayoutManager(activity, 2)
        recyclerGridView.adapter = adapter
    }

}