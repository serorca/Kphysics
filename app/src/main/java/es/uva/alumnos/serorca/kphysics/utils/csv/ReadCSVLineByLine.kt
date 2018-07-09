package es.uva.alumnos.serorca.kphysics.utils.csv

import com.opencsv.CSVReader
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException


class ReadCSVLineByLine {

    private var fileName = "+$FILE_NAME.csv"
    private var reader: CSVReader? = null

    fun readCSV() {
        try {
            reader = CSVReader(FileReader(fileName))
            val column: Array<String> = reader!!.readNext()
            while (reader!!.readNext() != null) {
                for (i in column.indices) {
                    println("Cell Value" + column[i])
                }
            }
        } catch (e: FileNotFoundException) {
            System.err.println(e.message)
        } catch (e: IOException) {
            System.err.println(e.message)

        }

    }

    companion object {
        const val FILE_NAME = "FILE_NAME"
        @JvmStatic
        fun main(s: Array<String>) {
            val readFile = ReadCSVLineByLine()
            readFile.readCSV()
        }
    }
}