package com.example.firecash

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas")
    fun obtenerVentas(): LiveData<List<Venta>>

    @Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    suspend fun insertarVentas(venta: Venta): Long

    @Delete
    suspend fun borrarVentas(venta: Venta): Int

    @Query("SELECT SUM(efectivo) FROM ventas WHERE timestamp BETWEEN :inicio AND :fin")
    suspend fun obtenerTotalEfectivo(inicio: Long, fin: Long): Double

    @Query("SELECT SUM(tarjeta) FROM ventas WHERE timestamp BETWEEN :inicio AND :fin")
    suspend fun obtenerTotalTarjeta(inicio: Long, fin: Long): Double
}
