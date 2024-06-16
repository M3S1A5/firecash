package com.example.firecash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView

class VentasAdapter(
    private val ventas: MutableList<Venta>,
    private val eliminarVentaCallback: (Venta) -> Unit,
    private val editarVentaCallback: (Venta) -> Unit
) : RecyclerView.Adapter<VentasAdapter.VentaViewHolder>() {

inner class VentaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ventaIdTextView: TextView = itemView.findViewById(R.id.venta_id)
    val ventaFechaTextView: TextView = itemView.findViewById(R.id.venta_fecha)
    val ventaMetodoPagoTextView: TextView = itemView.findViewById(R.id.venta_metodo_pago)
    val ventaTotalTextView: TextView = itemView.findViewById(R.id.venta_total)
    val botonEliminarVenta: AppCompatImageButton = itemView.findViewById(R.id.boton_eliminar_venta)
    val botonEditarVenta: AppCompatImageButton = itemView.findViewById(R.id.boton_editar_venta)

    init {
        botonEliminarVenta.setOnClickListener {
            eliminarVentaCallback(ventas[adapterPosition])
        }
        botonEditarVenta.setOnClickListener {
            editarVentaCallback(ventas[adapterPosition])
        }
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_venta, parent, false)
        return VentaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VentaViewHolder, position: Int) {
        val venta = ventas[position]
        holder.ventaIdTextView.text = venta.productoId.toString()
        holder.ventaFechaTextView.text = venta.fecha
        holder.ventaMetodoPagoTextView.text = venta.metodoPago
        holder.ventaTotalTextView.text = "${venta.total}€"
    }

    override fun getItemCount() = ventas.size

    fun updateVentas(newVentas: List<Venta>) {
        this.ventas.clear()
        this.ventas.addAll(newVentas)
        notifyDataSetChanged()
    }
}