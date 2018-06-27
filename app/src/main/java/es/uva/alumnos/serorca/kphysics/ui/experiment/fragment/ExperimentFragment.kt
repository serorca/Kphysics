package es.uva.alumnos.serorca.kphysics.ui.experiment.fragment

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import es.uva.alumnos.serorca.kphysics.R
import kotlinx.android.synthetic.main.fragment_sensor_view.*
import java.util.*
import java.util.concurrent.CompletableFuture.runAsync

class ExperimentFragment : Fragment(), ExperimentFragmentContract.View,
        View.OnClickListener,
        OnChartValueSelectedListener,
        SensorEventListener {

    companion object {

        const val ARG_TYPE_NAME = "ARG_TYPE_NAME"
    }

    private var sensorType: Int = 0
    private var sensorName: String? = null
    private var rawValues: FloatArray? = null
    private val sensorManager: SensorManager by lazy {
        activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sensor_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorName = this.arguments!!.getString(ARG_TYPE_NAME)

        when (sensorName) {
            "Accelerometer" -> sensorType = Sensor.TYPE_ACCELEROMETER
            "Ambient Temperature" -> sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE
            "Gravity" -> sensorType = Sensor.TYPE_GRAVITY
            "Gyroscope" -> sensorType = Sensor.TYPE_GYROSCOPE
            "Light" -> sensorType = Sensor.TYPE_LIGHT
            "Linear Acceleration" -> sensorType = Sensor.TYPE_LINEAR_ACCELERATION
            "Magnetic Field" -> sensorType = Sensor.TYPE_MAGNETIC_FIELD
            "Orientation" -> sensorType = Sensor.TYPE_ORIENTATION
            "Pressure" -> sensorType = Sensor.TYPE_PRESSURE
            "Proximity" -> sensorType = Sensor.TYPE_PROXIMITY
            "Relative Humidity" -> sensorType = Sensor.TYPE_RELATIVE_HUMIDITY
            "Rotation Vector" -> sensorType = Sensor.TYPE_ROTATION_VECTOR
            "Temperature" -> sensorType = Sensor.TYPE_TEMPERATURE
            else -> sensorType = Sensor.TYPE_ACCELEROMETER
        }

//        showLineGraph()
        btn_start.setOnClickListener(this)
        btn_pause.setOnClickListener(this)
        chart.setOnChartValueSelectedListener(this)
        chart.description.isEnabled
        chart.setTouchEnabled(true)
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(true)
        chart.setPinchZoom(true)
        chart.setBackgroundColor(Color.LTGRAY)

        val data = LineData()
        data.setValueTextColor(Color.WHITE)
        chart.data = data
    }


    override fun onClick(p0: View?) {
        if (p0!!.id == R.id.btn_start) {
            sensorManager.registerListener(
                    this,
                    sensorManager.getDefaultSensor(sensorType),
                    SensorManager.SENSOR_DELAY_NORMAL
            )
            feedMultiple()
        } else if (p0.id == R.id.btn_pause) {
            sensorManager.unregisterListener(this)
//            thread!!.interrupt()
        }
    }


    override fun showLineGraph() {

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(sensorType),
                SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
//        thread!!.interrupt()

    }

    override fun onNothingSelected() {

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x = event!!.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val format = "%s readings:\n x = %f\n y = %f\n z = %f"
        val message = String.format(Locale.getDefault(), format, sensorName, x, y, z)
        sensor.text = message
        rawValues = event.values

    }

    private fun addEntry() {

        val data = chart.data

        if (data != null) {

            var set: ILineDataSet? = data.getDataSetByIndex(0)

            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }

            data.addEntry(Entry(set.entryCount.toFloat(), rawValues!![0]), 0)
            data.notifyDataChanged()
            // let the chart know it's data has changed
            chart.notifyDataSetChanged()
            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(120f)
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.entryCount.toFloat())
//            chart.invalidate()
        }
    }

    private fun createSet(): LineDataSet {

        val set = LineDataSet(null, "Dynamic Data")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = ColorTemplate.getHoloBlue()
        set.setCircleColor(Color.WHITE)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.fillAlpha = 65
        set.fillColor = ColorTemplate.getHoloBlue()
        set.highLightColor = Color.rgb(244, 117, 117)
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 9f
        set.setDrawValues(false)
        set.setDrawCircles(false)
        return set
    }

    private var thread: Thread? = null

    private fun feedMultiple() {

        if (thread != null)
            thread!!.interrupt()

        val runnable = Runnable { addEntry() }

        thread = Thread(Runnable {
            for (i in 0..999) {

                // Don't generate garbage runnables inside the loop.
                runAsync(runnable)
                try {
                    Thread.sleep(300)
                } catch (e: InterruptedException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

            }
        })

        thread!!.start()
    }


}