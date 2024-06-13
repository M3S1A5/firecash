package com.example.firecash

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var campoNombreProducto: EditText
    private lateinit var campoPrecioProducto: EditText
    private lateinit var botonGuardarProducto: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        campoNombreProducto = findViewById(R.id.campo_nombre_producto)
        campoPrecioProducto = findViewById(R.id.campo_precio_producto)
        botonGuardarProducto = findViewById(R.id.boton_guardar_producto)

        botonGuardarProducto.setOnClickListener {
            val nombre = campoNombreProducto.text.toString()
            val precio = campoPrecioProducto.text.toString().toDoubleOrNull()

            if (nombre.isNotEmpty() && precio != null) {
                val producto = Producto(nombre = nombre, precio = precio)
                lifecycleScope.launch(Dispatchers.IO) {
                    AppVentasApplication.database.productoDao().insertarProducto(producto)
                }
                Toast.makeText(this, "Producto guardado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
