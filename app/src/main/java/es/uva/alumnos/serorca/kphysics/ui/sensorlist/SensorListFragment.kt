package es.uva.alumnos.serorca.kphysics.ui.sensorlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.uva.alumnos.serorca.kphysics.R
import es.uva.alumnos.serorca.kphysics.data.database.SensorInfo
import es.uva.alumnos.serorca.kphysics.di.component.DaggerFragmentComponent
import es.uva.alumnos.serorca.kphysics.di.module.FragmentModule
import kotlinx.android.synthetic.main.fragment_sensor_list.*
import javax.inject.Inject

class SensorListFragment : Fragment(), SensorContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    @Inject
    lateinit var presenter: SensorContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sensor_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SensorListAdapter(context!!)

        presenter.attach(this)
        presenter.subscribe()
        presenter.loadList()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun loadListSucces(list: ArrayList<SensorInfo>) {
//        val adapter = SensorListAdapter(context!!)
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = adapter
    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build()

        listComponent.inject(this)
    }

    companion object {
        val TAG: String = "SensorListFragment"

        fun newInstance() = SensorListFragment()
    }
}