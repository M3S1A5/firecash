package com.example.firecash

import androidx.lifecycle.LiveData

class VentaRepository(private val ventaDao: VentaDao) {
    val ventas: LiveData<List<Venta>> = ventaDao.obtenerVentas()

    suspend fun insertar(venta: Venta) {
        ventaDao.insertarVenta(venta)
    }

    fun obtenerTotalEfectivo(inicio: Long, fin: Long): LiveData<Double> {
        return ventaDao.obtenerTotalEfectivo(inicio, fin)
    }

    fun obtenerTotalTarjeta(inicio: Long, fin: Long): LiveData<Double> {
        return ventaDao.obtenerTotalTarjeta(inicio, fin)
    }
}
