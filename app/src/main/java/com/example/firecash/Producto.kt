package com.example.firecash

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class producto(
@PrimaryKey(autoGenerate = true) val id: Int = 0,
val nombre: String,
val precio: Double
)