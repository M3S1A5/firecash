package com.example.firecash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VentaViewModel(private val ventaRepository: VentaRepository) : ViewModel() {

    fun obtenerTotalEfectivo(inicio: Long, fin: Long): LiveData<Double> {
        return ventaRepository.obtenerTotalEfectivo(inicio, fin)
    }

    fun obtenerTotalTarjeta(inicio: Long, fin: Long): LiveData<Double> {
        return ventaRepository.obtenerTotalTarjeta(inicio, fin)
    }
}

class VentaViewModelFactory(
    private val repository: VentaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VentaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VentaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
