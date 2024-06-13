package com.example.firecash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductoViewModel(private val productoRoomDao: ProductoRoomDao) : ViewModel() {

    val allProductos: LiveData<List<Producto>> = productoRoomDao.getAllProductos()

    fun insert(producto: Producto) = viewModelScope.launch {
        productoRoomDao.insert(producto)
    }
}
