package com.example.firecash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VentaViewModel(private val ventaRepository: VentaRepository) : ViewModel() {

    val allVentas: LiveData<List<Venta>> = ventaRepository.allVentas

    fun insertarVenta(venta: Venta) = viewModelScope.launch {
        ventaRepository.insertarVenta(venta)
    }

    fun borrarVenta(venta: Venta) = viewModelScope.launch {
        ventaRepository.borrarVenta(venta)
    }

    fun obtenerTotalEfectivo(inicio: Long, fin: Long): LiveData<Double> {
        return ventaRepository.obtenerTotalEfectivo(inicio, fin)
    }

    fun obtenerTotalTarjeta(inicio: Long, fin: Long): LiveData<Double> {
        return ventaRepository.obtenerTotalTarjeta(inicio, fin)
    }
}
