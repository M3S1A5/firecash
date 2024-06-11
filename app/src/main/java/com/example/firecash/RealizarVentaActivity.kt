package com.example.firecash

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RealizarVentaActivity : AppCompatActivity() {

    private lateinit var listaProductos: ListView
    private lateinit var listaCarrito: ListView
    private lateinit var botonPagarEfectivo: Button
    private lateinit var botonPagarTarjeta: Button
    private val itemsCarrito = mutableListOf<Producto>() // Inicialización correcta de la lista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_venta)

        // Encontrar las vistas en el layout
        listaProductos = findViewById(R.id.lista_productos)
        listaCarrito = findViewById(R.id.lista_carrito)
        botonPagarEfectivo = findViewById(R.id.boton_pagar_efectivo)
        botonPagarTarjeta = findViewById(R.id.boton_pagar_tarjeta)

        // Cargar productos desde la base de datos y configurar la lista de productos
        CoroutineScope(Dispatchers.IO).launch {
            val productos = AppVentasApplication.database?.productoDao()?.obtenerProductos()
            runOnUiThread {
                // Configurar adaptador de la lista de productos
                productos?.let {
                    listaProductos.adapter = ProductoAdapter(this@RealizarVentaActivity, it.toMutableList(), ::agregarAlCarrito)
                }
            }
        }

        botonPagarEfectivo.setOnClickListener {
            finalizarVenta("efectivo")
        }

        botonPagarTarjeta.setOnClickListener {
            finalizarVenta("tarjeta")
        }
    }

    private fun agregarAlCarrito(producto: Producto) {
        itemsCarrito.add(producto)
        // Actualizar la lista del carrito
        listaCarrito.adapter = ProductoAdapter(this, itemsCarrito, ::agregarAlCarrito)
    }

    private fun finalizarVenta(metodoPago: String) {
        val montoTotal = itemsCarrito.sumOf { it.precio }
        val venta = Venta(monto = montoTotal, metodoPago = metodoPago, fecha = System.currentTimeMillis())

        CoroutineScope(Dispatchers.IO).launch {
            AppVentasApplication.database?.ventaDao()?.insertarVenta(venta)
            runOnUiThread {
                Toast.makeText(this@RealizarVentaActivity, "Venta realizada con éxito", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
