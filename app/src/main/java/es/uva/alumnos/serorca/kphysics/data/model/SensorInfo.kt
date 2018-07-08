package es.uva.alumnos.serorca.kphysics.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SensorInfo(@Expose
                      @SerializedName("sensor_name")
                      var sensorName: String? = null,

                      @Expose
                      @SerializedName("sensor_text")
                      var sensorText: String? = null,

                      @Expose
                      @SerializedName("img_id")
                      var sensorImg: Int = 0)

