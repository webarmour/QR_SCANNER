package ru.webarmour.qrzxing.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [ItemDb::class],
    version = 1,
    exportSchema = false,
    )

abstract class MainDatabase : RoomDatabase() {

    abstract fun getDao(): ItemsDao


    companion object {

        private var INSTANCE: MainDatabase? = null

        private val LOCK = Any()

        private const val DB_NAME = "MainDatabase"

        fun getInstance(context: Context): MainDatabase {
            INSTANCE?.let { return it }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val database = Room.databaseBuilder(
                    context,
                    MainDatabase::class.java,
                    name = DB_NAME
                ).build()
                INSTANCE = database
                return database
            }

        }
    }

}