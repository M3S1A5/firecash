package com.example.firecash

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TotalDiaActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_dia)

        db = DatabaseHelper(this)

        val fechaInicioInput: EditText = findViewById(R.id.fecha_inicio_input)
        val fechaFinInput: EditText = findViewById(R.id.fecha_fin_input)
        val resultadoTotal: TextView = findViewById(R.id.resultado_total)
        val botonCalcular: Button = findViewById(R.id.boton_calcular_total)

        botonCalcular.setOnClickListener {
            val fechaInicio = fechaInicioInput.text.toString()
            val fechaFin = fechaFinInput.text.toString()
            val total = calcularTotalVentas(fechaInicio, fechaFin)
            resultadoTotal.text = "Total de ventas del $fechaInicio al $fechaFin: $total"
        }
    }

    private fun calcularTotalVentas(fechaInicio: String, fechaFin: String): Double {
        val ventas = db.obtenerVentasEntreFechas(fechaInicio, fechaFin)
        return ventas.sumOf { it.total }
    }
}
