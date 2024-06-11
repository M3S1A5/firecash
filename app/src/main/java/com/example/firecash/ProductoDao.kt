package com.example.firecash

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductoDao {
    @Insert
    suspend fun insertarProducto(producto: Producto)

    @Delete
    suspend fun borrarProducto(producto: Producto)

    @Query("SELECT * FROM productos")
    fun obtenerProductos(): LiveData<List<Producto>>
}
