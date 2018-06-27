package es.uva.alumnos.serorca.kphysics.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import es.uva.alumnos.serorca.kphysics.data.database.proyects.Proyect

//TODO add my own dataclasses such as proyect
@Database(entities = [(Proyect::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

//    abstract fun
//
//    abstract fun

}