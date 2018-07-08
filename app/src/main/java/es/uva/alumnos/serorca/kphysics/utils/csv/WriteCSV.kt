package es.uva.alumnos.serorca.kphysics.utils.csv

import com.opencsv.CSVWriter
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.StatefulBeanToCsvBuilder
import es.uva.alumnos.serorca.kphysics.data.model.Project
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object WriteCSV {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val csv = args[0] + ".csv"
        val writer = CSVWriter(FileWriter(csv))

        //Create record
        val record = args[1]
                .split(",".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
        //Write the record to file
        writer.writeNext(record)

        //close the writer
        writer.close()
    }
}