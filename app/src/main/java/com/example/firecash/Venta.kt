package com.example.firecash

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ventas")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val cantidad: Int,
    val metodoPago: String, // "Efectivo" o "Tarjeta"
    val fecha: Long
)