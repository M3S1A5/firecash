package com.example.firecash

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    fun getAllProductos(): LiveData<List<Producto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(producto: Producto): Long

    @Delete
    suspend fun delete(producto: Producto): Int
}
