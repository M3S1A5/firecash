package com.example.firecash

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "firecash.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_PRODUCTOS = "productos"
        private const val COLUMN_PRODUCTO_ID = "id"
        private const val COLUMN_PRODUCTO_NOMBRE = "nombre"
        private const val COLUMN_PRODUCTO_PRECIO = "precio"

        private const val TABLE_VENTAS = "ventas"
        private const val COLUMN_VENTA_ID = "id"
        private const val COLUMN_VENTA_FECHA = "fecha"
        private const val COLUMN_VENTA_METODO_PAGO = "metodo_pago"
        private const val COLUMN_VENTA_TOTAL = "total"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createProductosTable = ("CREATE TABLE $TABLE_PRODUCTOS ("
                + "$COLUMN_PRODUCTO_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_PRODUCTO_NOMBRE TEXT,"
                + "$COLUMN_PRODUCTO_PRECIO REAL)")

        val createVentasTable = ("CREATE TABLE $TABLE_VENTAS ("
                + "$COLUMN_VENTA_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_VENTA_FECHA TEXT,"
                + "$COLUMN_VENTA_METODO_PAGO TEXT,"
                + "$COLUMN_VENTA_TOTAL REAL)")

        db.execSQL(createProductosTable)
        db.execSQL(createVentasTable)

        //insertamos productos iniciales en la base de datos.
        insertarProducto(db, "Traca Chinos", 0.50);
        insertarProducto(db, "Mecha (3 unidades)", 1.00);
        insertarProducto(db, "Camelia Flower Pequeña", 1.50);
        insertarProducto(db, "Tubo Rosa", 1.50);
        insertarProducto(db, "Traca Pájaro", 1.50);
        insertarProducto(db, "Bengala Normal", 1.50);
        insertarProducto(db, "Linces", 2.00);
        insertarProducto(db, "Dinosaurios", 2.00);
        insertarProducto(db, "Abejitas", 2.00);
        insertarProducto(db, "Cohete Trueno", 2.00);
        insertarProducto(db, "Splash", 2.50);
        insertarProducto(db, "Aguilas", 3.00);
        insertarProducto(db, "Chinos", 3.00);
        insertarProducto(db, "Piulas", 3.00);
        insertarProducto(db, "Rojo Boom", 4.00);
        insertarProducto(db, "Super Falleros (25 unds.)", 4.00);
        insertarProducto(db, "Loki", 5.00);
        insertarProducto(db, "Trueno TNT", 5.00);
        insertarProducto(db, "Kamikaze", 6.00);
        insertarProducto(db, "Estallido FULMINANTES PARA CIGARROS", 1.00);
        insertarProducto(db, "TRUENO ZOOMBI", 7.00);
        insertarProducto(db, "TRUENO SUPER BOOM", 9.00);
        insertarProducto(db, "BLANCO-DORADO TRUENO SOPLETE", 5.00);
        insertarProducto(db, "Poca luz TRUENO SILBA BOOM", 7.00);
        insertarProducto(db, "TRUENO RED BANG", 10.00);
        insertarProducto(db, "TRUENO PLATA Ó PLOMO", 8.00);
        insertarProducto(db, "TRUENO PANTERA NEGRA", 4.00);
        insertarProducto(db, "PETARDOS FRANKIS", 2.00);
        insertarProducto(db, "TRUENO FALLERO", 4.00);
        insertarProducto(db, "PETARDOS DINOS", 7.00);


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_VENTAS")
        onCreate(db)
    }

    fun insertarProducto(db: SQLiteDatabase, nombre: String, precio: Double) {
        val values = ContentValues()
        values.put(COLUMN_PRODUCTO_NOMBRE, nombre)
        values.put(COLUMN_PRODUCTO_PRECIO, precio)
        db.insert(TABLE_PRODUCTOS, null, values)
    }

    fun obtenerProductos(): List<Producto> {
        val productos = mutableListOf<Producto>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCTOS", null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_NOMBRE))
                val precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_PRECIO))
                productos.add(Producto(nombre, precio, 1))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return productos
    }

    fun eliminarProducto(nombre: String) {
        val db = this.writableDatabase
        db.delete(TABLE_PRODUCTOS, "$COLUMN_PRODUCTO_NOMBRE=?", arrayOf(nombre))
        db.close()
    }

    fun actualizarProducto(nombreAntiguo: String, nombreNuevo: String, precioNuevo: Double) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PRODUCTO_NOMBRE, nombreNuevo)
        values.put(COLUMN_PRODUCTO_PRECIO, precioNuevo)
        db.update(TABLE_PRODUCTOS, values, "$COLUMN_PRODUCTO_NOMBRE=?", arrayOf(nombreAntiguo))
        db.close()
    }

    fun insertarVenta(fecha: String, metodoPago: String, total: Double) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_VENTA_FECHA, fecha)
        values.put(COLUMN_VENTA_METODO_PAGO, metodoPago)
        values.put(COLUMN_VENTA_TOTAL, total)
        db.insert(TABLE_VENTAS, null, values)
        db.close()
    }

    fun obtenerProductoPorNombre(nombre: String): Producto? {
        val db = this.readableDatabase
        var producto: Producto? = null
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_PRODUCTOS WHERE $COLUMN_PRODUCTO_NOMBRE=?",
            arrayOf(nombre)
        )
        if (cursor.moveToFirst()) {
            val nombreProducto =
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_NOMBRE))
            val precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_PRECIO))
            producto = Producto(
                nombreProducto,
                precio,
                1
            ) // Aquí asumo una cantidad inicial de 1, puedes ajustarlo según tu lógica.
        }
        cursor.close()
        db.close()
        return producto
    }

    fun obtenerNombresProductos(): List<String> {
        val nombresProductos = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_PRODUCTO_NOMBRE FROM $TABLE_PRODUCTOS", null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_NOMBRE))
                nombresProductos.add(nombre)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return nombresProductos
    }
    fun eliminarVenta(id: Int) {
    val db = this.writableDatabase
    db.delete(TABLE_VENTAS, "$COLUMN_VENTA_ID=?", arrayOf(id.toString()))
    db.close()
}

fun editarVenta(id: Int, fecha: String, metodoPago: String, total: Double) {
    val db = this.writableDatabase
    val values = ContentValues()
    values.put(COLUMN_VENTA_FECHA, fecha)
    values.put(COLUMN_VENTA_METODO_PAGO, metodoPago)
    values.put(COLUMN_VENTA_TOTAL, total)
    db.update(TABLE_VENTAS, values, "$COLUMN_VENTA_ID=?", arrayOf(id.toString()))
    db.close()
}

    fun obtenerTodasLasVentas(): MutableList<Venta> {
        val ventas = mutableListOf<Venta>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_VENTAS", null)
        if (cursor.moveToFirst()) {
            do {
                val productoId =
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VENTA_ID)) // Obtener productoId
                val cantidad =
                    1 // Asumir una cantidad inicial de 1, puedes ajustarlo según tu lógica.
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VENTA_FECHA))
                val metodoPago =
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VENTA_METODO_PAGO))
                val total = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VENTA_TOTAL))
                ventas.add(
                    Venta(
                        productoId,
                        cantidad,
                        fecha,
                        metodoPago,
                        total
                    )
                ) // Pasar todos los parámetros necesarios
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return ventas
    }
    fun obtenerTotalVentasEfectivoEntreFechas(fechaInicio: String, fechaFin: String): Double {
    var total = 0.0
    val db = this.readableDatabase
    val cursor = db.rawQuery(
        "SELECT SUM($COLUMN_VENTA_TOTAL) FROM $TABLE_VENTAS WHERE $COLUMN_VENTA_FECHA BETWEEN ? AND ? AND $COLUMN_VENTA_METODO_PAGO = ?",
        arrayOf(fechaInicio, fechaFin, "Efectivo")
    )
    if (cursor.moveToFirst() && !cursor.isNull(0)) {
        total = cursor.getDouble(0)
    }
    cursor.close()
    db.close()
    return total
}

fun obtenerTotalVentasTarjetaEntreFechas(fechaInicio: String, fechaFin: String): Double {
    var total = 0.0
    val db = this.readableDatabase
    val cursor = db.rawQuery(
        "SELECT SUM($COLUMN_VENTA_TOTAL) FROM $TABLE_VENTAS WHERE $COLUMN_VENTA_FECHA BETWEEN ? AND ? AND $COLUMN_VENTA_METODO_PAGO = ?",
        arrayOf(fechaInicio, fechaFin, "Tarjeta")
    )
    if (cursor.moveToFirst() && !cursor.isNull(0)) {
        total = cursor.getDouble(0)
    }
    cursor.close()
    db.close()
    return total
}
}