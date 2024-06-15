package com.example.firecash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
}
