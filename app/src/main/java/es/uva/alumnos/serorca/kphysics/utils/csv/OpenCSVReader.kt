package es.uva.alumnos.serorca.kphysics.utils.csv

import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.Reader


object OpenCSVReader {

    fun readCSVFile(name: String) {

        var fileReader: BufferedReader? = null
        var reader: CSVReader? = null

        try {
            fileReader = BufferedReader(FileReader("$name.csv") as Reader?)
            reader = CSVReader(fileReader)

            var record: Array<String>?
//            csvReader.readNext() // skip Header

            record = CSVReader(fileReader).readNext()
            while (record != null) {
                println("XXXXXXXX" + record[0] + " | " + record[1])
                record = CSVReader(fileReader).readNext()
            }

            reader.close()

            // -------------------------------------------
//            println("\n--- Read all at once ---")
//
//            fileReader = BufferedReader(FileReader("customer.csv"))
//            csvReader = CSVReaderBuilder(fileReader).withSkipLines(1).build()
//
//            val records = csvReader.readAll()
//            for (_record in records) {
//                println(_record[0] + " | " + _record[1])
//            }
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