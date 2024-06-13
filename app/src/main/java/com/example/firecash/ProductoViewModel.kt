package com.example.firecash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductoViewModel(private val productoDao: ProductoDao) : ViewModel() {

    val allProductos: LiveData<List<Producto>> = productoDao.getAllProductos()

    fun insert(producto: Producto) = viewModelScope.launch {
        productoDao.insert(producto)
    }
}
