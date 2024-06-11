package com.example.firecash

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Producto::class, Venta::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun ventaDao(): VentaDao
}
