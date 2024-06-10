package com.example.firecash

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VentaDao {
    @Insert
    suspend fun insertarVenta(venta: Venta)

    @Query("SELECT * FROM ventas")
    fun obtenerVentas(): LiveData<List<Venta>>

    @Query("SELECT SUM(precio * cantidad) FROM ventas INNER JOIN productos ON ventas.productoId = productos.id WHERE metodoPago = 'Efectivo' AND fecha BETWEEN :inicio AND :fin")
    fun obtenerTotalEfectivo(inicio: Long, fin: Long): LiveData<Double>

    @Query("SELECT SUM(precio * cantidad) FROM ventas INNER JOIN productos ON ventas.productoId = productos.id WHERE metodoPago = 'Tarjeta' AND fecha BETWEEN :inicio AND :fin")
    fun obtenerTotalTarjeta(inicio: Long, fin: Long): LiveData<Double>
}