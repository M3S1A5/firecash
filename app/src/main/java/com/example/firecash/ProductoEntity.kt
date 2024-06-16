package com.example.firecash

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var nombre: String,
    var precio: Double,
    var cantidad: Int = 1
)