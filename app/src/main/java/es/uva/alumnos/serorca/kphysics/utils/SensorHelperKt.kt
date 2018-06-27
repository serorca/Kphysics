package es.uva.alumnos.serorca.kphysics.utils

import android.widget.TextView
import es.uva.alumnos.serorca.sensorlibrary.ReactiveSensorFilter
import es.uva.alumnos.serorca.sensorlibrary.ReactiveSensors
import es.uva.alumnos.serorca.sensorlibrary.SensorNotFoundException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

internal class SensorHelperKt(private val reactiveSensors: ReactiveSensors,
                              private val sensorType: Int,
                              private val sensorName: String?,
                              private val textViewForMessage: TextView) {

    fun createSubscription(): Disposable {
        return reactiveSensors.observeSensor(sensorType)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(ReactiveSensorFilter.filterSensorChanged())
                .subscribe({ reactiveSensorEvent ->
                    val event = reactiveSensorEvent.sensorEvent

                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    val format = "%s readings:\n x = %f\n y = %f\n z = %f"
                    val message = String.format(Locale.getDefault(), format, sensorName, x, y, z)
                    textViewForMessage.text = message
                }, { throwable ->
                    if (throwable is SensorNotFoundException) {
                        textViewForMessage.text = "Sorry, your device doesn't have required sensor."
                    }
                })
    }

    fun rawValues(): FloatArray? {

        var values: FloatArray? = null
        var auxvalue = floatArrayOf(1.toFloat(), 2.toFloat(), 3.toFloat())

        reactiveSensors.observeSensor(sensorType)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(ReactiveSensorFilter.filterSensorChanged())
                .subscribe({ reactiveSensorEvent ->
                    val event = reactiveSensorEvent.sensorEvent

                    values = event.values

                }, { throwable ->
                    if (throwable is SensorNotFoundException) {
                        textViewForMessage.text = "Sorry, your device doesn't have required sensor."
                    }
                })
//        if (values == null)
//            return auxvalue
//        else
            return values
    }

    fun safelyDispose(disposable: Disposable?) {
        if (!reactiveSensors.hasSensor(sensorType)) {
            return
        }

        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }
}