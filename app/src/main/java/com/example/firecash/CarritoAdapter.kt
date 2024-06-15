package com.example.firecash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarritoAdapter(
    private val carrito: MutableList<Producto>,
    private val onDeleteClickListener: (Producto) -> Unit,
    private val onEditClickListener: (Producto) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView? = itemView.findViewById(R.id.text_nombre_producto)
        val precioTextView: TextView? = itemView.findViewById(R.id.text_precio_producto)
        val cantidadTextView: TextView? = itemView.findViewById(R.id.text_cantidad_producto)
        val editarButton: ImageButton? = itemView.findViewById(R.id.boton_editar_producto)
        val eliminarButton: ImageButton? = itemView.findViewById(R.id.boton_eliminar_producto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = carrito[position]

        holder.nombreTextView?.text = producto.nombre
        holder.precioTextView?.text = "Precio: ${producto.precio}"
        holder.cantidadTextView?.text = "Cantidad: ${producto.cantidad}"

        // Configurar clic para el botón de eliminar
        holder.eliminarButton?.setOnClickListener {
            onDeleteClickListener(producto)
        }

        // Configurar clic para el botón de editar
        holder.editarButton?.setOnClickListener {
            onEditClickListener(producto)
        }
    }

    override fun getItemCount(): Int {
        return carrito.size
    }
}