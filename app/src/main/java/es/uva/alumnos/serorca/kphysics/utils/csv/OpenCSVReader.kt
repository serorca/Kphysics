package es.uva.alumnos.serorca.kphysics.utils.csv

import android.widget.TextView
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.Reader
import java.util.*


object OpenCSVReader {

    fun readCSVFile(dir: String, textView: TextView) {

        var fileReader: BufferedReader? = null
        var reader: CSVReader? = null

        try {
            fileReader = BufferedReader(FileReader("$dir.csv") as Reader?)
            reader = CSVReader(fileReader)

            var record: Array<String>?

            record = CSVReader(fileReader).readNext()
            while (record != null) {
                val format = "x(time) = %s  y(value) = %s\n"
                val message = String.format(
                        Locale.getDefault(), format, record[0], record[1])
                textView.append(message)
                record = CSVReader(fileReader).readNext()
            }

            reader.close()

        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } finally {
            try {
                fileReader!!.close()
                reader!!.close()
            } catch (e: IOException) {
                println("Closing fileReader/csvParser Error!")
                e.printStackTrace()
            }
        }
    }

}