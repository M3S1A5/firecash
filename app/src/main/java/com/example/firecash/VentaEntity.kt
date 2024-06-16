package com.example.firecash

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VentaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var productoId: Int,
    var cantidad: Int,
    var fecha: String,
    var metodoPago: String,
    var total: Double
)
