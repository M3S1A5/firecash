package com.example.firecash

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ventas")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val monto: Double,
    val metodoPago: String, // "efectivo" o "tarjeta"
    val fecha: Long // Timestamp para la fecha de la venta
)
