package com.example.firecash

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VentaDao {
    @Insert
    suspend fun insertarVenta(venta: Venta)

    @Query("SELECT * FROM ventas")
    suspend fun obtenerVentas(): List<Venta>

    @Query("SELECT SUM(monto) FROM ventas WHERE metodoPago = :metodo")
    suspend fun totalPorMetodoPago(metodo: String): Double?
}
