package com.example.firecash

import androidx.room.Entity

@Entity(tableName = "productos")
data class Producto(
    val nombre: String,
    val precio: Double
)
