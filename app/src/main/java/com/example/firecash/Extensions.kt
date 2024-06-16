package com.example.firecash

// Conversiones para ProductoEntity y Producto
fun ProductoEntity.toProducto(): Producto {
    return Producto(
        nombre = this.nombre,
        precio = this.precio,
        cantidad = this.cantidad
    )
}

fun Producto.toProductoEntity(): ProductoEntity {
    return ProductoEntity(
        id = 0, // Room se encargará de generar el ID
        nombre = this.nombre,
        precio = this.precio,
        cantidad = this.cantidad
    )
}
fun VentaEntity.toVenta(): Venta {
    return Venta(
        productoId = this.productoId,
        cantidad = this.cantidad,
        fecha = this.fecha,
        metodoPago = this.metodoPago,
        total = this.total
    )
}
fun Venta.toVentaEntity(): VentaEntity {
    return VentaEntity(
        id = 0, // Room se encargará de generar el ID
        productoId = this.productoId,
        cantidad = this.cantidad,
        fecha = this.fecha,
        metodoPago = this.metodoPago,
        total = this.total
    )
}