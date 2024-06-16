package com.example.firecash

data class Venta(
    val productoId: Int,
    val cantidad: Int,
    val fecha: String,
    var metodoPago: String,
    var total: Double
)
