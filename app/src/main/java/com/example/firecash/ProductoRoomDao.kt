package com.example.firecash

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface ProductoRoomDao {
    @Query("SELECT * FROM productos")
    fun obtenerProductos(): LiveData<List<Producto>>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun obtenerProductoPorId(id: Int): Producto?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarProducto(producto: Producto): Long

    @Delete
    suspend fun borrarProducto(producto: Producto): Int
}
