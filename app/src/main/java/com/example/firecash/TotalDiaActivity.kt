package com.example.firecash

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TotalDiaActivity : AppCompatActivity() {

    private lateinit var textoTotalEfectivo: TextView
    private lateinit var textoTotalTarjeta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_dia)

        // Encontrar las vistas en el layout
        textoTotalEfectivo = findViewById(R.id.texto_total_efectivo)
        textoTotalTarjeta = findViewById(R.id.texto_total_tarjeta)

        // Calcular el total del día por método de pago
        CoroutineScope(Dispatchers.IO).launch {
            val totalEfectivo = AppVentasApplication.database?.ventaDao()?.totalPorMetodoPago("efectivo") ?: 0.0
            val totalTarjeta = AppVentasApplication.database?.ventaDao()?.totalPorMetodoPago("tarjeta") ?: 0.0

            runOnUiThread {
                textoTotalEfectivo.text = "Total en Efectivo: $${totalEfectivo}"
                textoTotalTarjeta.text = "Total con Tarjeta: $${totalTarjeta}"
            }
        }
    }
}
