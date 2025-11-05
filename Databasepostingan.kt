package com.uti.posttest5


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Postingan::class], version = 1, exportSchema = false)
abstract class DatabasePostingan : RoomDatabase() {

    abstract fun postinganDao(): PostinganDao

    companion object {
        @Volatile
        private var INSTANCE: DatabasePostingan? = null

        fun getDatabase(context: Context): DatabasePostingan {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabasePostingan::class.java,
                    "postingan_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
