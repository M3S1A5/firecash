package com.example.firecash

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class EditarProductosActivity : AppCompatActivity() {

    private lateinit var productosRecyclerView: RecyclerView
    private lateinit var productosAdapter: ProductosAdapter
    private lateinit var db: DatabaseHelper
    private val productos: MutableList<Producto> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_productos)

        try {
            db = DatabaseHelper(this)
            productos.addAll(db.obtenerProductos())

            productosRecyclerView = findViewById(R.id.productos_recycler_view)
            productosRecyclerView.layoutManager = LinearLayoutManager(this)
            productosAdapter = ProductosAdapter(productos,
                eliminarProductoCallback = { producto -> eliminarProducto(producto) },
                editarProductoCallback = { producto -> editarProducto(producto) }
            )
            productosRecyclerView.adapter = productosAdapter

            val botonAgregarProducto: Button = findViewById(R.id.boton_agregar_producto)
            botonAgregarProducto.setOnClickListener { agregarProducto() }
            val botonVolver: Button = findViewById(R.id.boton_volver)
            botonVolver.setOnClickListener { finish() }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun agregarProducto() {
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val nombreInput = EditText(this)
        nombreInput.hint = "Nombre del Producto"
        layout.addView(nombreInput)

        val precioInput = EditText(this)
        precioInput.hint = "Precio del Producto"
        layout.addView(precioInput)

        builder.setView(layout)
        builder.setPositiveButton("Agregar") { _, _ ->
            val nombre = nombreInput.text.toString()
            val precioStr = precioInput.text.toString()

            if (nombre.isEmpty() || precioStr.isEmpty()) {
                Toast.makeText(this, "El nombre y el precio no pueden estar vacíos", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val precio = precioStr.toDouble()
                    val dbWritable = db.writableDatabase
                    db.insertarProducto(dbWritable, nombre, precio)
                    productos.add(Producto(nombre, precio))
                    productosAdapter.notifyDataSetChanged()
                    dbWritable.close()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al agregar producto: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

 private fun eliminarProducto(producto: Producto) {
    AlertDialog.Builder(this)
        .setTitle("Eliminar producto")
        .setMessage("Estás a punto de eliminar el producto ${producto.nombre}. ¿Estás seguro?")
        .setPositiveButton("Aceptar") { _, _ ->
            try {
                db.eliminarProducto(producto.nombre)
                productos.remove(producto)
                productosAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al eliminar producto: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        .setNegativeButton("Cancelar", null)
        .show()
}

    private fun editarProducto(producto: Producto) {
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        // Establecer el nombre actual del producto
        val nombreInput = EditText(this)
        nombreInput.hint = "Nuevo Nombre del Producto"
        nombreInput.setText(producto.nombre)
        layout.addView(nombreInput)

        // Establecer el precio actual del producto
        val precioInput = EditText(this)
        precioInput.hint = "Nuevo Precio del Producto"
        precioInput.setText(producto.precio.toString())
        layout.addView(precioInput)

        builder.setView(layout)
        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevoNombre = nombreInput.text.toString()
            val nuevoPrecioStr = precioInput.text.toString()
            try {
                val nuevoPrecio = nuevoPrecioStr.toDouble()
                db.actualizarProducto(producto.nombre, nuevoNombre, nuevoPrecio)
                producto.nombre = nuevoNombre
                producto.precio = nuevoPrecio
                productosAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al editar producto: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }
}