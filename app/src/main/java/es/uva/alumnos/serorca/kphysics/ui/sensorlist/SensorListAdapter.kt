package es.uva.alumnos.serorca.kphysics.ui.sensorlist

import android.content.Context
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import es.uva.alumnos.serorca.kphysics.R
import es.uva.alumnos.serorca.kphysics.ui.experiment.activity.ExperimentActivity

class SensorListAdapter(
        private val context: Context) : RecyclerView.Adapter<SensorListAdapter.SensorListViewHolder>() {

    private val titles = arrayOf(
            "Accelerometer",
            "Ambient Temperature",
            "Gravity",
            "Gyroscope",
            "Light",
            "Linear Acceleration",
            "Magnetic Field",
            "Orientation",
            "Pressure",
            "Proximity",
            "Relative Humidity",
            "Rotation Vector",
            "Temperature")

    private val description = arrayOf(
            "Item one details",
            "Item two details",
            "Item three details",
            "Item four details",
            "Item five details",
            "Item six details",
            "Item seven details",
            "Item eight details",
            "Item nine details",
            "Item ten details",
            "Item eleven details",
            "Item twelve details",
            "Item thirteen details")

    private val images = intArrayOf(
            R.drawable.ic_sensor,
            R.drawable.ic_multi_chart,
            R.drawable.ic_sensor_potentiometer,
            R.drawable.ic_line_chart,
            R.drawable.ic_line_chart,
            R.drawable.ic_sensor_pressure,
            R.drawable.ic_line_chart,
            R.drawable.ic_line_chart,
            R.drawable.ic_line_chart,
            R.drawable.ic_sensor_prox,
            R.drawable.ic_line_chart,
            R.drawable.ic_line_chart,
            R.drawable.ic_sensor_pressure)


    override fun getItemCount() = this.titles.size

    override fun onBindViewHolder(holder: SensorListViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = description[position]
        holder.itemImage.setImageResource(images[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorListViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sensor_list, parent, false)
        return SensorListViewHolder(v)
    }

//    internal fun addSensorsToList(sensorList: ArrayList<SensorInfo>) {
//        this.sensorList.addAll(sensorList)
//        notifyDataSetChanged()
//    }

    inner class SensorListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView = itemView.findViewById(R.id.sensor_image)
        var itemTitle: TextView = itemView.findViewById(R.id.sensor_name)
        var itemDetail: TextView = itemView.findViewById(R.id.sensor_info)

        init {
            itemView.setOnClickListener { _ ->
                val position: Int = adapterPosition
                val dumbArray: BooleanArray = booleanArrayOf(false)

                val intent = ExperimentActivity.newIntent(context, dumbArray, position,
                        null, false)
                startActivity(context, intent, null)

            }
        }

        /////////////otra manera////////////////
//        fun clear() {
//            itemView.sensor_image.setImageDrawable(null)
//            itemView.sensor_info.text = ""
//            itemView.sensor_name.text = ""
//        }
//
//        fun onBind(position: Int) {
//            val (sensorName, sensorText, sensorImg) = sensorList[position]
//
//            inflateData(sensorName, sensorText, sensorImg)
//            setItemClickListener(sensorName)
//        }
//
//        private fun setItemClickListener(sensorName: String?) {
//            itemView.setOnClickListener {
//            }
//        }
//
//        private fun inflateData(title: String?, description: String?, sensorImg: Drawable?) {
//            title?.let { itemView.sensor_name.text = it }
//            description?.let { itemView.sensor_info.text = it }
//            sensorImg?.let {
//                itemView.sensor_image.setImageDrawable(it)
//            }
//        }
    }

}