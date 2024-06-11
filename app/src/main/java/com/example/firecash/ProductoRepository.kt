package com.example.firecash

import androidx.lifecycle.LiveData
import com.example.firecash.ProductoRepository
class ProductoRepository(private val productoDao: ProductoDao) {
    val productos: LiveData<List<Producto>> = productoDao.obtenerProductos()

    suspend fun insertar(producto: Producto) {
        productoDao.insertarProducto(producto)
    }
}