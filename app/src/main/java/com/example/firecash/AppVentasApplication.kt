package com.example.firecash

import android.app.Application
import androidx.room.Room

class AppVentasApplication : Application() {
    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "ventas-db"
        ).build()
    }
}
