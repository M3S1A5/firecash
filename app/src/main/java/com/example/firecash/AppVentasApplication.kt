package com.example.firecash

import android.app.Application
import androidx.room.Room

class AppVentasApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "firecash_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
