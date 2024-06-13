package com.example.firecash

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RealizarVentaActivity : AppCompatActivity() {

    private lateinit var ventaViewModel: VentaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VentaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_venta)

        recyclerView = findViewById(R.id.recycler_view_ventas)
        adapter = VentaListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        ventaViewModel = ViewModelProvider(this).get(VentaViewModel::class.java)

        ventaViewModel.allVentas.observe(this, Observer { ventas ->
            ventas?.let { adapter.setVentas(it) }
        })

        val botonRealizarVenta: Button = findViewById(R.id.boton_realizar_venta)
        botonRealizarVenta.setOnClickListener {
            // Implementar la lógica para realizar una venta
            Toast.makeText(this, "Venta realizada", Toast.LENGTH_SHORT).show()
        }
    }
}
