package com.desuzed.newsapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleDTO::class], version = 1, exportSchema = false)
abstract class RoomDbApp : RoomDatabase() {
    abstract fun articleDao(): ArticleDAO

    companion object {
        @Volatile
        private var INSTANCE: RoomDbApp? = null

        fun getDatabase(context: Context): RoomDbApp {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDbApp::class.java,
                    "room_app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}