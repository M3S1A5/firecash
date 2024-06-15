package com.example.firecash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductosAdapter(
    private val productos: List<Producto>,
    private val eliminarProductoCallback: (Producto) -> Unit,
    private val editarProductoCallback: (Producto) -> Unit // Callback para editar producto
) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombre_producto)
        val precioTextView: TextView = itemView.findViewById(R.id.precio_producto)
        val editarButton: ImageButton = itemView.findViewById(R.id.boton_editar_producto)
        val eliminarButton: ImageButton = itemView.findViewById(R.id.boton_eliminar_producto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombreTextView.text = producto.nombre
        holder.precioTextView.text = "Precio: $${producto.precio}"

        // clic para eliminar producto
        holder.eliminarButton.setOnClickListener {
            eliminarProductoCallback(producto)
        }

        //clic para editar producto
        holder.editarButton.setOnClickListener {
            editarProductoCallback(producto)
        }
    }

    override fun getItemCount() = productos.size
}
