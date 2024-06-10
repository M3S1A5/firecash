package com.example.firecash

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class VentaViewModel(private val repository: VentaRepository) : ViewModel() {
    val ventas: LiveData<List<Venta>> = repository.ventas

    fun insertar(venta: Venta) = viewModelScope.launch {
        repository.insertar(venta)
    }

    fun obtenerTotalEfectivo(inicio: Long, fin: Long): LiveData<Double> {
        return repository.obtenerTotalEfectivo(inicio, fin)
    }

    fun obtenerTotalTarjeta(inicio: Long, fin: Long): LiveData<Double> {
        return repository.obtenerTotalTarjeta(inicio, fin)
    }
}

class VentaViewModelFactory(private val repository: VentaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VentaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VentaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
