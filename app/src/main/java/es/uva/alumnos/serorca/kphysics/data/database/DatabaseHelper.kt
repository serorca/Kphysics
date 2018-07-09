package es.uva.alumnos.serorca.kphysics.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import es.uva.alumnos.serorca.kphysics.data.model.Project

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "project_db_3.db", null, 3) {

    companion object {
        const val TABLE_EXPERIMENT: String = "TABLE_EXPERIMENT"
        const val ID: String = "ID_"
        const val NAME: String = "NAME"
        const val FILE: String = "FILE"
    }

    private val EXPERIMENT_DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_EXPERIMENT + " (" +
                    "$ID integer PRIMARY KEY autoincrement," +
                    "$NAME text," +
                    "$FILE text" +
                    ")"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(EXPERIMENT_DATABASE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun insertProjectData(name: String, file: String): Long {
        val values = ContentValues()
        values.put(NAME, name)
        values.put(FILE, file)
        return writableDatabase.insert(TABLE_EXPERIMENT, null, values)
    }

    fun getAllProjectData(): MutableList<Project> {
        val projList: MutableList<Project> = mutableListOf()
        val cursor: Cursor = readableDatabase.query(TABLE_EXPERIMENT, arrayOf(NAME, FILE), null, null, null, null, null)
        cursor.use {
            if (it.count != 0) {
                it.moveToFirst()
                if (it.count > 0) {
                    do {
                        val name: String = it.getString(it.getColumnIndex(NAME))
                        val file: String = it.getString(it.getColumnIndex(FILE))
                        projList.add(Project(name, file))
                    } while ((it.moveToNext()))
                }
            }
        }

        return projList
    }

    fun getParticularProjectData(name: String): Project {
        val db = this.readableDatabase
        var project: Project? = null
        val selectQuery = "SELECT  * FROM $TABLE_EXPERIMENT WHERE $NAME = '$name'"
        val cursor = db.rawQuery(selectQuery, null)
        cursor.use {
            if (it.count != 0) {
                it.moveToFirst()
                if (it.count > 0) {
                    do {
                        val projectName = it.getString(it.getColumnIndex(NAME))
                        val file: String = it.getString(it.getColumnIndex(FILE))
                        project = Project(projectName, file)
                    } while ((it.moveToNext()))
                }
            }
        }

        return project!!
    }
}
