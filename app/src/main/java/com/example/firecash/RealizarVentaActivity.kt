package com.example.firecash

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.widget.TextView
import android.text.InputType

class RealizarVentaActivity : AppCompatActivity() {

    private lateinit var carritoRecyclerView: RecyclerView
    private lateinit var carritoAdapter: CarritoAdapter
    private val carrito: MutableList<Producto> = mutableListOf()
    private lateinit var db: DatabaseHelper
    private lateinit var autoCompleteAdapter: ArrayAdapter<String>
    private lateinit var subtotalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_venta)

        db = DatabaseHelper(this)

        carritoRecyclerView = findViewById(R.id.carrito_recycler_view)
        carritoRecyclerView.layoutManager = LinearLayoutManager(this)
        carritoAdapter = CarritoAdapter(
            carrito,
            onDeleteClickListener = { producto -> eliminarProducto(producto) },
            onEditClickListener = { producto -> editarProducto(producto) }
        )
        carritoRecyclerView.adapter = carritoAdapter

        autoCompleteAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, mutableListOf())

        val botonAgregarProducto: Button = findViewById(R.id.boton_agregar_producto)
        botonAgregarProducto.setOnClickListener { agregarProducto() }

        val botonRealizarCompra: Button = findViewById(R.id.boton_realizar_compra)
        botonRealizarCompra.setOnClickListener { realizarCompra() }
        subtotalTextView = findViewById(R.id.subtotal_text_view)
    }

    private fun actualizarSubtotal() {
        val subtotal = carrito.sumOf { it.precio * it.cantidad }
        subtotalTextView.text = "Subtotal: $subtotal€"
    }

    private fun agregarProducto() {
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val autoCompleteTextView = AutoCompleteTextView(this)
        autoCompleteTextView.setAdapter(autoCompleteAdapter)
        autoCompleteTextView.hint = "Nombre del Producto"
        layout.addView(autoCompleteTextView)

        val cantidadInput = EditText(this)
        cantidadInput.hint = "Cantidad"
        cantidadInput.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(cantidadInput)

        builder.setView(layout)
        builder.setPositiveButton("Agregar") { _, _ ->
            val nombre = autoCompleteTextView.text.toString()
            val cantidad = cantidadInput.text.toString().toIntOrNull() ?: 0

            if (nombre.isNotBlank() && cantidad > 0) {
                lifecycleScope.launch(Dispatchers.Main) {
                    val producto = obtenerProductoPorNombre(nombre)
                    if (producto != null) {
                        carrito.add(Producto(producto.nombre, producto.precio, cantidad))
                        carritoAdapter.notifyDataSetChanged()
                        actualizarSubtotal()
                    } else {
                        Toast.makeText(
                            this@RealizarVentaActivity,
                            "Producto no encontrado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Por favor ingrese un nombre y cantidad válidos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()

        // Cargar los nombres de productos para el autocompletado
        lifecycleScope.launch(Dispatchers.Main) {
            val nombresProductos = obtenerNombresProductosDesdeBD()
            autoCompleteAdapter.clear()
            autoCompleteAdapter.addAll(nombresProductos)
            autoCompleteAdapter.notifyDataSetChanged()
        }
        actualizarSubtotal()
    }

    private fun editarProducto(producto: Producto) {
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val nombreInput = EditText(this)
        nombreInput.hint = "Nuevo nombre"
        layout.addView(nombreInput)

        val cantidadInput = EditText(this)
        cantidadInput.hint = "Nueva cantidad"
        cantidadInput.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(cantidadInput)

        // Prellenar los campos con los valores actuales del producto
        nombreInput.setText(producto.nombre)
        cantidadInput.setText(producto.cantidad.toString())

        builder.setView(layout)
        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevoNombre = nombreInput.text.toString()
            val nuevaCantidad = cantidadInput.text.toString().toInt()

            // Actualizar el producto en la lista `carrito`
            val index = carrito.indexOf(producto)
            if (index != -1) {
                val productoEditado = Producto(nuevoNombre, nuevaCantidad.toDouble())
                carrito[index] = productoEditado
                carritoAdapter.notifyDataSetChanged()
                actualizarSubtotal()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun eliminarProducto(producto: Producto) {
        carrito.remove(producto)
        carritoAdapter.notifyDataSetChanged()
        actualizarSubtotal()
    }

    private fun realizarCompra() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Método de Pago")
        builder.setMessage("Seleccione el método de pago")
        builder.setPositiveButton("Efectivo") { _, _ -> guardarVenta("Efectivo") }
        builder.setNegativeButton("Tarjeta") { _, _ -> guardarVenta("Tarjeta") }
        builder.show()
    }

    private fun guardarVenta(metodoPago: String) {
        val total = carrito.sumOf { it.precio * it.cantidad }
        val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        db.insertarVenta(fecha, metodoPago, total)

        // Mostrar un AlertDialog con el total de la venta
        AlertDialog.Builder(this)
            .setTitle("Venta realizada")
            .setMessage("Venta realizada con $metodoPago. Total: $total€")
            .setPositiveButton("OK", null)
            .show()

        carrito.clear()
        carritoAdapter.notifyDataSetChanged()
        actualizarSubtotal()
    }

    // Método para obtener nombres de productos desde la base de datos
    private suspend fun obtenerNombresProductosDesdeBD(): List<String> {
        return db.obtenerNombresProductos()
    }

    // Método para obtener un producto por su nombre desde la base de datos
    private suspend fun obtenerProductoPorNombre(nombre: String): Producto? {
        return db.obtenerProductoPorNombre(nombre)
    }
}