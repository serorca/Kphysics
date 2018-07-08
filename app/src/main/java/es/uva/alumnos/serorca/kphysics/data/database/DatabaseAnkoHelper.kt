package es.uva.alumnos.serorca.kphysics.data.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseAnkoHelper(context: Context) : ManagedSQLiteOpenHelper(context, "DataBase", null, 1) {

    val Context.database: DatabaseAnkoHelper
        get() = DatabaseAnkoHelper.getInstance(applicationContext)

    companion object {
        private var instance: DatabaseAnkoHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseAnkoHelper {
            if (instance == null) {
                instance = DatabaseAnkoHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.createTable("Project", true,
                "id" to INTEGER + PRIMARY_KEY + UNIQUE + AUTOINCREMENT,
                "name" to TEXT,
                "data" to BLOB)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertProjectData (name: String): Long{
        val db = writableDatabase
        return db.insert("Project",
                "name" to name)
    }

//    fun getAllProjects(): MutableList<Project>{
//        val projList: MutableList<Project> = mutableListOf()
//        val db = readableDatabase
//        val cursor : Cursor = db.select("Project", null))
//                .whereArgs(null, null, )
//
//    }

}