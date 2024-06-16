package com.example.firecash

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TotalDiaActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private var fechaInicio: String = ""
    private var fechaFin: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_dia)

        db = DatabaseHelper(this)

        val fechaInicioButton: Button = findViewById(R.id.fecha_inicio_button)
        val fechaFinButton: Button = findViewById(R.id.fecha_fin_button)
        val botonCalcular: Button = findViewById(R.id.boton_calcular_total)
        val botonVolver: Button = findViewById(R.id.boton_volver1)
        botonVolver.setOnClickListener { finish() }
        fechaInicioButton.setOnClickListener {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
            fechaInicio = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            fechaInicioButton.text = fechaInicio
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}

fechaFinButton.setOnClickListener {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
            fechaFin = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            fechaFinButton.text = fechaFin
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}

botonCalcular.setOnClickListener {
    try {
        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            Toast.makeText(this, "Por favor, seleccione las fechas de inicio y fin", Toast.LENGTH_SHORT).show()
        } else {
            val totalEfectivo = db.obtenerTotalVentasEfectivoEntreFechas(fechaInicio, fechaFin)
            val totalTarjeta = db.obtenerTotalVentasTarjetaEntreFechas(fechaInicio, fechaFin)
            val total = totalEfectivo + totalTarjeta

            AlertDialog.Builder(this)
                .setTitle("Total de ventas")
                .setMessage("Total en efectivo: $totalEfectivo\nTotal con tarjeta: $totalTarjeta\nTotal general: $total")
                .setPositiveButton("OK", null)
                .show()
        }
    } catch (e: Exception) {
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
    }
}
    }
}