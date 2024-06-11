package com.example.firecash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firecash.dataBinding.ItemProductoBinding

class ProductoListAdapter : ListAdapter<Producto, ProductoListAdapter.ProductoViewHolder>(ProductosComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ProductoViewHolder(private val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(producto: Producto) {
            // Aquí puedes establecer los datos del producto en los elementos de la vista
            binding.textViewNombre.text = producto.nombre
            binding.textViewPrecio.text = producto.precio.toString()
            // Y así sucesivamente con otros elementos de la vista
        }
    }
}

class ProductosComparator : DiffUtil.ItemCallback<Producto>() {
    override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
        return oldItem == newItem
    }
}