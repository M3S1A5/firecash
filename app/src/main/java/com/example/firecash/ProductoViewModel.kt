package com.example.firecash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductoViewModel(private val productoRepository: ProductoRepository) : ViewModel() {
    val productos = productoRepository.getAllProductos()

    fun insertar(producto: Producto) {
        productoRepository.insert(producto)
    }
}

class ProductoViewModelFactory(
    private val repository: ProductoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
