package com.example.firecash

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BorrarProductoActivity : AppCompatActivity() {

    private lateinit var campoIdProducto: EditText
    private lateinit var botonBorrarProducto: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borrar_producto)

        // Encontrar las vistas en el layout
        campoIdProducto = findViewById(R.id.campo_id_producto)
        botonBorrarProducto = findViewById(R.id.boton_borrar_producto)

        botonBorrarProducto.setOnClickListener {
            val id = campoIdProducto.text.toString().toIntOrNull()

            if (id != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val productoDao = AppVentasApplication.database?.productoDao()
                    val producto = productoDao?.obtenerProductoPorId(id)
                    if (producto != null) {
                        productoDao.borrarProducto(producto)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@BorrarProductoActivity, "Producto borrado", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@BorrarProductoActivity, "Producto no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, introduce un ID válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
