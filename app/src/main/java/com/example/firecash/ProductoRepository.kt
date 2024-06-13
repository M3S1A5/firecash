package com.example.firecash

import androidx.lifecycle.LiveData

class ProductoRepository(private val productoRoomDao: ProductoRoomDao) {
    val productos: LiveData<List<Producto>> = productoRoomDao.obtenerProductos()

    suspend fun insertar(producto: Producto) {
        productoRoomDao.insertarProducto(producto)
    }
}
