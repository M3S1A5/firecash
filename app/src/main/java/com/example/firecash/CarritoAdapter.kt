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
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView? = itemView.findViewById(R.id.text_nombre_producto)
        val precioTextView: TextView? = itemView.findViewById(R.id.text_precio_producto)
        val cantidadTextView: TextView? = itemView.findViewById(R.id.text_cantidad_producto)
        val editarButton: ImageButton? = itemView.findViewById(R.id.boton_editar_producto)
        val eliminarButton: ImageButton? = itemView.findViewById(R.id.boton_eliminar_producto)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            val producto = carrito[position - 1]
            (holder as ItemViewHolder).nombreTextView?.text = producto.nombre
            holder.precioTextView?.text = "${producto.precio}"
            holder.cantidadTextView?.text = "${producto.cantidad}"

            // Configurar clic para el botón de eliminar
            holder.eliminarButton?.setOnClickListener {
                onDeleteClickListener(producto)
            }

            // Configurar clic para el botón de editar
            holder.editarButton?.setOnClickListener {
                onEditClickListener(producto)
            }
        }
    }

    override fun getItemCount(): Int {
        return carrito.size + 1
    }
}