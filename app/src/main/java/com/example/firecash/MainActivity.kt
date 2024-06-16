package com.example.firecash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    private lateinit var productoDao: ProductoDao
    private lateinit var ventaDao: VentaDao
    private lateinit var productosAdapter: ProductosAdapter
    private lateinit var ventasAdapter: VentasAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa la base de datos, el ProductoDao y el VentaDao
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        productoDao = db.productoDao()
        ventaDao = db.ventaDao()

        // Inicializa los adaptadores y configura los RecyclerViews
        productosAdapter = ProductosAdapter(
            mutableListOf(),
            eliminarProductoCallback = { producto -> },
            editarProductoCallback = { producto ->  }
        )
        ventasAdapter = VentasAdapter(mutableListOf(),
            eliminarVentaCallback = { venta -> },
            editarVentaCallback = { venta ->  }
        )


        // Carga los productos y las ventas de la base de datos
        cargarProductos()
        cargarVentas()

        // Botón para realizar venta
        val botonRealizarVenta: Button = findViewById(R.id.boton_realizar_venta)
        botonRealizarVenta.setOnClickListener {
            val intent = Intent(this, RealizarVentaActivity::class.java)
            startActivity(intent)
        }

        // Botón para editar productos
        val botonEditarProductos: Button = findViewById(R.id.boton_editar_productos)
        botonEditarProductos.setOnClickListener {
            val intent = Intent(this, EditarProductosActivity::class.java)
            startActivity(intent)
        }

        // Botón para calcular el total del día
        val botonTotalDia: Button = findViewById(R.id.boton_total_dia)
        botonTotalDia.setOnClickListener {
            val intent = Intent(this, TotalDiaActivity::class.java)
            startActivity(intent)
        }
        // Botón para ver las ventas
        val botonVerVentas: Button = findViewById(R.id.boton_ver_ventas)
        botonVerVentas.setOnClickListener {
            val intent = Intent(this, VentasActivity::class.java)
            startActivity(intent)
}
    }

    private fun cargarProductos() {
        // Carga los productos desde la base de datos en un hilo diferente
        Thread {
            val productos = productoDao.getAll()

            // Actualiza el ProductosAdapter en el hilo principal
            runOnUiThread {
                productosAdapter.updateProductos(productos.map { it.toProducto() })
            }
        }.start()
    }

private fun cargarVentas() {
    // Carga las ventas desde la base de datos en un hilo diferente
    Thread {
        val ventasEntity = ventaDao.getAll()

        // Convierte cada VentaEntity en Venta
        val ventas = ventasEntity.map { it.toVenta() }

        // Actualiza el VentasAdapter en el hilo principal
        runOnUiThread {
            ventasAdapter.updateVentas(ventas)
        }
    }.start()
}
}