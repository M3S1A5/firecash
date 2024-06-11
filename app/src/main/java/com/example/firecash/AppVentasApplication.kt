package com.example.firecash

import android.app.Application

class AppVentasApplication : Application() {
    val productoRepository: ProductoRepository
        get() = // Inicializa el repositorio de productos

    val ventaRepository: VentaRepository
        get() = // Inicializa el repositorio de ventas
}
