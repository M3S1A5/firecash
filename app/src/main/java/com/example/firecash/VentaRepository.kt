package com.example.firecash

import androidx.lifecycle.LiveData

class VentaRepository(private val ventaDao: VentaDao) {

    val allVentas: LiveData<List<Venta>> = ventaDao.obtenerVentas()

    suspend fun insertarVenta(venta: Venta) {
        ventaDao.insertarVentas(venta)
    }

    suspend fun borrarVenta(venta: Venta) {
        ventaDao.borrarVentas(venta)
    }

    suspend fun obtenerTotalEfectivo(inicio: Long, fin: Long): Double {
        return ventaDao.obtenerTotalEfectivo(inicio, fin)
    }

    suspend fun obtenerTotalTarjeta(inicio: Long, fin: Long): Double {
        return ventaDao.obtenerTotalTarjeta(inicio, fin)
    }
}
