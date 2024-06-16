package com.example.firecash

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner

class VentasActivity : AppCompatActivity() {

    private lateinit var ventasRecyclerView: RecyclerView
    private lateinit var ventasAdapter: VentasAdapter
    private lateinit var db: DatabaseHelper
    private val ventas: MutableList<Venta> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventas)

        try {
            db = DatabaseHelper(this)
            ventasRecyclerView = findViewById(R.id.ventas_recycler_view)
            ventasRecyclerView.layoutManager = LinearLayoutManager(this)
            ventasAdapter = VentasAdapter(ventas,
                eliminarVentaCallback = { venta -> eliminarVenta(venta) },
                editarVentaCallback = { venta -> editarVenta(venta) }
            )
            ventasRecyclerView.adapter = ventasAdapter
            val botonVolver: Button = findViewById(R.id.boton_volver)
            botonVolver.setOnClickListener { finish() }
            cargarVentas()
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun cargarVentas() {
        val ventas = db.obtenerTodasLasVentas()
        ventasAdapter.updateVentas(ventas)
        ventasAdapter.notifyDataSetChanged()
    }

    private fun eliminarVenta(venta: Venta) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar venta")
            .setMessage("Estás a punto de eliminar la venta de ${venta.productoId}. ¿Estás seguro?")
            .setPositiveButton("Aceptar") { _, _ ->
                try {
                    db.eliminarVenta(venta.productoId)
                    ventas.remove(venta)
                    ventasAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al eliminar venta: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

private fun editarVenta(venta: Venta) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Editar venta")

    val spinnerMetodoPago = Spinner(this)
    val metodosPago = arrayOf("Efectivo", "Tarjeta")
    spinnerMetodoPago.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, metodosPago)
    spinnerMetodoPago.setSelection(metodosPago.indexOf(venta.metodoPago))

    val inputTotal = EditText(this)
    inputTotal.inputType = InputType.TYPE_CLASS_NUMBER
    inputTotal.setText(venta.total.toString())

    val layout = LinearLayout(this)
    layout.orientation = LinearLayout.VERTICAL
    layout.addView(spinnerMetodoPago)
    layout.addView(inputTotal)
    builder.setView(layout)

    builder.setPositiveButton("Aceptar") { dialog, _ ->
        venta.metodoPago = spinnerMetodoPago.selectedItem.toString()
        venta.total = inputTotal.text.toString().toDouble()
        ventasAdapter.notifyDataSetChanged()
        dialog.dismiss()
    }

    builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

    builder.show()
}
}