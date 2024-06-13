import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RealizarVentaActivity : AppCompatActivity(), VentaListAdapter.OnDeleteClickListener {

    private lateinit var ventaViewModel: VentaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VentaListAdapter

    private val carritoProductos = mutableListOf<Venta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_venta)

        recyclerView = findViewById(R.id.recycler_view_ventas)
        adapter = VentaListAdapter(this)
        adapter.setOnDeleteClickListener(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        ventaViewModel = ViewModelProvider(this).get(VentaViewModel::class.java)

        // Configurar AutoCompleteTextView para productos
        val autoCompleteTextView: AutoCompleteTextView = findViewById(R.id.auto_complete_producto)
        val productosArray = arrayOf("Producto A", "Producto B", "Producto C", "Producto D") // Ejemplo: lista de productos disponibles
        val adapterProductos = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, productosArray)
        autoCompleteTextView.setAdapter(adapterProductos)

        val botonAgregarProducto: Button = findViewById(R.id.boton_agregar_producto)
        botonAgregarProducto.setOnClickListener {
            agregarProductoAlCarrito()
        }

        val botonPagar: Button = findViewById(R.id.boton_pagar)
        botonPagar.setOnClickListener {
            mostrarDialogoPago()
        }
    }

    private fun agregarProductoAlCarrito() {
        val productoNombreCodigo = findViewById<AutoCompleteTextView>(R.id.auto_complete_producto).text.toString()
        val cantidad = obtenerCantidadDesdeInterfaz()

        if (productoNombreCodigo.isNotEmpty() && cantidad > 0) {
            val nuevaVenta = Venta(productoId = 0, cantidad = cantidad, efectivo = 0.0, tarjeta = 0.0, nombreCodigoProducto = productoNombreCodigo)
            carritoProductos.add(nuevaVenta)
            adapter.setVentas(carritoProductos)
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDeleteClick(position: Int) {
        carritoProductos.removeAt(position)
        adapter.setVentas(carritoProductos)
    }

    private fun mostrarDialogoPago() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar Método de Pago")
        val opciones = arrayOf("Efectivo", "Tarjeta")
        builder.setItems(opciones) { dialog, which ->
            val metodoPago = opciones[which]
            procesarPago(metodoPago)
        }
        builder.show()
    }

    private fun procesarPago(metodoPago: String) {
        // Calcular el total a pagar
        val totalPagar = calcularTotalPagar()

        // Dependiendo del método de pago seleccionado, actualizar los montos en las ventas
        when (metodoPago) {
            "Efectivo" -> {
                for (venta in carritoProductos) {
                    venta.efectivo = venta.totalVenta()
                }
            }
            "Tarjeta" -> {
                for (venta in carritoProductos) {
                    venta.tarjeta = venta.totalVenta()
                }
            }
        }

        // Insertar ventas en la base de datos usando ViewModel y CoroutineScope
        for (venta in carritoProductos) {
            insertarVenta(venta)
        }

        // Limpiar el carrito después del pago
        carritoProductos.clear()
        adapter.setVentas(carritoProductos)

        Toast.makeText(this, "Pago realizado con éxito: $metodoPago", Toast.LENGTH_SHORT).show()
    }

    private fun insertarVenta(venta: Venta) {
        ventaViewModel.insertarVenta(venta)
    }

    private fun calcularTotalPagar(): Double {
        var total = 0.0
        for (venta in carritoProductos) {
            total += venta.totalVenta()
        }
        return total
    }

    private fun obtenerCantidadDesdeInterfaz(): Int {
        val cantidadStr = findViewById<EditText>(R.id.edit_text_cantidad).text.toString()
        return if (cantidadStr.isNotEmpty()) {
            cantidadStr.toInt()
        } else {
            0
        }
    }
}
