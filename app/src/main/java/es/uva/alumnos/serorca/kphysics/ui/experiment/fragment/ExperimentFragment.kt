package es.uva.alumnos.serorca.kphysics.ui.experiment.fragment

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.Visibility
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
import es.uva.alumnos.serorca.kphysics.ui.experiment.activity.ExperimentActivity
import es.uva.alumnos.serorca.kphysics.ui.experiment.activity.ExperimentActivity.Companion.CURRENT_EXPERIMENT
import es.uva.alumnos.serorca.kphysics.ui.experiment.activity.ExperimentTabAdapter
import es.uva.alumnos.serorca.kphysics.utils.csv.WriteCSV
import kotlinx.android.synthetic.main.fragment_sensor_view.*
import java.util.*
import java.util.concurrent.CompletableFuture.runAsync

class ExperimentFragment : Fragment(), ExperimentFragmentContract.View,
        View.OnClickListener,
        OnChartValueSelectedListener,
        SensorEventListener {

    companion object {
        const val ARG_TYPE_NAME = "ARG_TYPE_NAME"
//        const val CURRENT_EXPERIMENT = "current_experiment"

    }

    private var sensorType: Int = 0
    private var sensorName: String? = null
    private var isMultiple: Boolean? = null
    private var rawValues: FloatArray? = null
    private var currentProject: String? = null
    private val sensorManager: SensorManager by lazy {
        activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sensor_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentProject = activity!!.intent.getStringExtra(CURRENT_EXPERIMENT)
        showLineGraph()
        btn_start.setOnClickListener(this)
        btn_pause.setOnClickListener(this)
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
        sensorName = this.arguments!!.getString(ARG_TYPE_NAME)

        when (sensorName) {
            "Accelerometer" -> {
                sensorType = Sensor.TYPE_ACCELEROMETER
                isMultiple = true
            }
            "Ambient Temperature" -> {
                sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE
                isMultiple = false
            }
            "Gravity" -> {
                sensorType = Sensor.TYPE_GRAVITY
                isMultiple = true
            }
            "Gyroscope" -> {
                sensorType = Sensor.TYPE_GYROSCOPE
                isMultiple = true
            }
            "Light" -> {
                sensorType = Sensor.TYPE_LIGHT
                isMultiple = false
            }
            "Linear Acceleration" -> {
                sensorType = Sensor.TYPE_LINEAR_ACCELERATION
                isMultiple = false
            }
            "Magnetic Field" -> {
                sensorType = Sensor.TYPE_MAGNETIC_FIELD
                isMultiple = false
            }
            "Orientation" -> {
                sensorType = Sensor.TYPE_ORIENTATION
                isMultiple = true
            }
            "Pressure" -> {
                sensorType = Sensor.TYPE_PRESSURE
                isMultiple = false
            }
            "Proximity" -> {
                sensorType = Sensor.TYPE_PROXIMITY
                isMultiple = false
            }
            "Relative Humidity" -> {
                sensorType = Sensor.TYPE_RELATIVE_HUMIDITY
                isMultiple = false
            }
            "Rotation Vector" -> {
                sensorType = Sensor.TYPE_ROTATION_VECTOR
                isMultiple = true
            }
            "Temperature" -> {
                sensorType = Sensor.TYPE_TEMPERATURE
                isMultiple = false
            }
            else -> {
                sensorType = Sensor.TYPE_ACCELEROMETER
                isMultiple = true
            }
        }

        //chart
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

        if (isMultiple!!) {

            //chart2
            chart2.visibility = View.VISIBLE
            chart2.setOnChartValueSelectedListener(this)
            chart2.description.isEnabled
            chart2.setTouchEnabled(true)
            chart2.setScaleEnabled(true)
            chart2.setDrawGridBackground(true)
            chart2.setPinchZoom(true)
            chart2.setBackgroundColor(Color.LTGRAY)
            val data2 = LineData()
            data2.setValueTextColor(Color.WHITE)
            chart2.data = data2

            //chart3
            chart3.visibility = View.VISIBLE
            chart3.setOnChartValueSelectedListener(this)
            chart3.description.isEnabled
            chart3.setTouchEnabled(true)
            chart3.setScaleEnabled(true)
            chart3.setDrawGridBackground(true)
            chart3.setPinchZoom(true)
            chart3.setBackgroundColor(Color.LTGRAY)
            val data3 = LineData()
            data3.setValueTextColor(Color.WHITE)
            chart3.data = data3
        }
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

        if (isMultiple == null) {
            isMultiple = y.compareTo(z).compareTo(0.0) != 0
        }
    }

    private fun addEntry() {

        val data = chart.data
        val data2 = chart2.data
        val data3 = chart3.data

        if (data != null) {

            var set: ILineDataSet? = data.getDataSetByIndex(0)

            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }

            //CSV creation
            if (currentProject != null) {
                val entryString = arrayOf(currentProject!!, set.entryCount.toString()
                        + "," + rawValues!![0].toString())
                WriteCSV.main(entryString, context!!.filesDir)
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

        if (data2 != null) {

            var set: ILineDataSet? = data2.getDataSetByIndex(0)

            if (set == null) {
                set = createSet()
                data2.addDataSet(set)
            }

            data2.addEntry(Entry(set.entryCount.toFloat(), rawValues!![1]), 0)
            data2.notifyDataChanged()
            // let the chart know it's data has changed
            chart2.notifyDataSetChanged()
            // limit the number of visible entries
            chart2.setVisibleXRangeMaximum(120f)
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart2.moveViewToX(data.entryCount.toFloat())
//            chart.invalidate()
        }

        if (data3 != null) {

            var set: ILineDataSet? = data3.getDataSetByIndex(0)

            if (set == null) {
                set = createSet()
                data3.addDataSet(set)
            }

            data3.addEntry(Entry(set.entryCount.toFloat(), rawValues!![2]), 0)
            data3.notifyDataChanged()
            // let the chart know it's data has changed
            chart3.notifyDataSetChanged()
            // limit the number of visible entries
            chart3.setVisibleXRangeMaximum(120f)
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart3.moveViewToX(data.entryCount.toFloat())
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
                    e.printStackTrace()
                }

            }
        })

        thread!!.start()
    }


}