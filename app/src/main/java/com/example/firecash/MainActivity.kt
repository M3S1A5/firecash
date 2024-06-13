package com.example.firecash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Encontrar los botones en el layout
        val botonAgregarProducto: Button = findViewById(R.id.boton_agregar_producto)
        val botonBorrarProducto: Button = findViewById(R.id.boton_borrar_producto)
        val botonRealizarVenta: Button = findViewById(R.id.boton_realizar_venta)
        val botonTotalDia: Button = findViewById(R.id.boton_total_dia)

        // Configurar el listener para el botón de agregar producto
        botonAgregarProducto.setOnClickListener {
            val intent = Intent(this, AgregarProductoActivity::class.java)
            startActivity(intent)
        }

        // Configurar el listener para el botón de borrar producto
        botonBorrarProducto.setOnClickListener {
            val intent = Intent(this, BorrarProductoActivity::class.java)
            startActivity(intent)
        }

        // Configurar el listener para el botón de realizar venta
        botonRealizarVenta.setOnClickListener {
            val intent = Intent(this, RealizarVentaActivity::class.java)
            startActivity(intent)
        }

        // Configurar el listener para el botón de total del día
        botonTotalDia.setOnClickListener {
            val intent = Intent(this, TotalDiaActivity::class.java)
            startActivity(intent)
        }
    }
}
