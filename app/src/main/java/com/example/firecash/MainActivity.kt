package com.example.firecash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appventas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val productoViewModel: ProductoViewModel by viewModels {
        ProductoViewModelFactory((application as AppVentasApplication).productoRepository)
    }

    private val ventaViewModel: VentaViewModel by viewModels {
        VentaViewModelFactory((application as AppVentasApplication).ventaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView
        val adapter = ProductoListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        productoViewModel.productos.observe(this) { productos ->
            productos?.let { adapter.submitList(it) }
        }

        binding.botonAgregarProducto.setOnClickListener {
            // Lógica para agregar un nuevo producto
            val producto = Producto(nombre = "Nuevo Producto", precio = 10.0)
            productoViewModel.insertar(producto)
        }

        binding.botonTotalDia.setOnClickListener {
            // Lógica para mostrar el total del día
            val inicio = // tiempo de inicio del día
            val fin = // tiempo de fin del día
                ventaViewModel.obtenerTotalEfectivo(inicio, fin).observe(this) { totalEfectivo ->
                    // Mostrar total en efectivo
                }
            ventaViewModel.obtenerTotalTarjeta(inicio, fin).observe(this) { totalTarjeta ->
                // Mostrar total en tarjeta
            }
        }
    }
}
