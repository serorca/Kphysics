package es.uva.alumnos.serorca.kphysics.utils.csv

import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.Reader


class OpenCSVReader {

    fun readCSVFile(name: String) {

        var fileReader: BufferedReader? = null
        var csvReader: CSVReader? = null

        try {
            fileReader = BufferedReader(FileReader("$name.csv") as Reader?)
            csvReader = CSVReader(fileReader)

            var record: Array<String>?
            csvReader.readNext() // skip Header

            record = csvReader.readNext()
            while (record != null) {
                println(record[0] + " | " + record[1] + " | " + record[2] + " | " + record[3])
                record = csvReader.readNext()
            }

            csvReader.close()

            // -------------------------------------------
            println("\n--- Read all at once ---")

            fileReader = BufferedReader(FileReader("customer.csv"))
            csvReader = CSVReaderBuilder(fileReader).withSkipLines(1).build()

            val records = csvReader.readAll()
            for (_record in records) {
                println(_record[0] + " | " + _record[1] + " | " + _record[2] + " | " + _record[3])
            }
        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } finally {
            try {
                fileReader!!.close()
                csvReader!!.close()
            } catch (e: IOException) {
                println("Closing fileReader/csvParser Error!")
                e.printStackTrace()
            }
        }
    }

}