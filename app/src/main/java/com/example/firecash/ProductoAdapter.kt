package com.example.firecash

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ProductoAdapter(
    private val context: Context,
    private val productos: MutableList<Producto>,
    private val agregarAlCarrito: (Producto) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = productos.size

    override fun getItem(position: Int): Any = productos[position]

    override fun getItemId(position: Int): Long = productos[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false)
        val producto = productos[position]

        val nombreProducto: TextView = view.findViewById(R.id.nombre_producto)
        val precioProducto: TextView = view.findViewById(R.id.precio_producto)

        nombreProducto.text = producto.nombre
        precioProducto.text = "$${producto.precio}"

        view.setOnClickListener {
            agregarAlCarrito(producto)
        }

        return view
    }

    // Método para actualizar la lista de productos en el adaptador
    fun actualizarProductos(nuevosProductos: List<Producto>) {
        productos.clear()
        productos.addAll(nuevosProductos)
        notifyDataSetChanged()
    }
}
