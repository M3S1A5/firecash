package com.example.firecash

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val productoId: Int,
    val cantidad: Int,
    val efectivo: Double,
    val tarjeta: Double
)
