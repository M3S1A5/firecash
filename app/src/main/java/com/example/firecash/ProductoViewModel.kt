package com.example.firecash

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ProductoViewModel(private val repository: ProductoRepository) : ViewModel() {
    val productos: LiveData<List<Producto>> = repository.productos

    fun insertar(producto: Producto) = viewModelScope.launch {
        repository.insertar(producto)
    }
}

class ProductoViewModelFactory(private val repository: ProductoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
