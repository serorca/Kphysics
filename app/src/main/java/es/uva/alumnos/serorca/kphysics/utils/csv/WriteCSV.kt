package es.uva.alumnos.serorca.kphysics.utils.csv

import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter

object WriteCSV {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>, filesDir: File) {
        val file = File(filesDir.toString() + "/" + args[0] + ".csv")
        val writer = CSVWriter(FileWriter(file, true))

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