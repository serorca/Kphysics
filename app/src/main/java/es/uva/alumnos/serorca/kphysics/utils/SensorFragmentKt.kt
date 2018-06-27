package es.uva.alumnos.serorca.kphysics.utils

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.uva.alumnos.serorca.kphysics.R
import es.uva.alumnos.serorca.sensorlibrary.ReactiveSensors
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_sensor_view.*

abstract class SensorFragmentKt : Fragment() {
    protected var sensorType: Int = 0
    protected var sensorName: String? = null
    protected var sensorText: String? = null

    private var reactiveSensors: ReactiveSensors? = null
    private var subscription: Disposable? = null
    private var sensorHelper: SensorHelperKt? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sensor_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reactiveSensors = ReactiveSensors(context)
        sensorHelper = SensorHelperKt(reactiveSensors!!, sensorType, sensorName, sensor)
    }

    override fun onResume() {
        super.onResume()
        subscription = sensorHelper!!.createSubscription()
    }

    override fun onPause() {
        super.onPause()
        sensorHelper!!.safelyDispose(subscription)
    }
}