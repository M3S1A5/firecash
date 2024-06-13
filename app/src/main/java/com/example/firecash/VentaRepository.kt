package com.example.firecash

import androidx.lifecycle.LiveData

class VentaRepository(private val ventaDao: VentaDao) {

    val allVentas: LiveData<List<Venta>> = ventaDao.obtenerVentas()

    suspend fun obtenerTotalEfectivo(): LiveData<Double> {
        return ventaDao.obtenerTotalEfectivo()
    }

    suspend fun obtenerTotalTarjeta(): LiveData<Double> {
        return ventaDao.obtenerTotalTarjeta()
    }
}
