package com.example.firecash

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.databinding.DataBindingComponent
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods

@InverseBindingMethods(
    InverseBindingMethod(
        type = TextView::class,
        attribute = "android:text",
        event = "android:textAttrChanged",
        method = "getText"
    )
)
class ItemProductoBindingImpl : ItemProductoBinding {
    // Declaraciones de variables para los elementos de la vista
    var textViewNombre: TextView
    var textViewPrecio: TextView

    constructor(root: View) : super(root) {
        // Inicializar los elementos de la vista
        textViewNombre = root.findViewById(R.id.textViewNombre)
        textViewPrecio = root.findViewById(R.id.textViewPrecio)
    }

    // Métodos de utilidad y configuración de los elementos de la vista
}

@BindingMethods(
    BindingMethod(
        type = TextView::class,
        attribute = "android:text",
        method = "setText"
    )
)
class DataBindingComponentImpl : DataBindingComponent {
    override fun getItemProductoBinding(): ItemProductoBinding {
        return ItemProductoBindingImpl()
    }
}

// Métodos de enlace de datos personalizados
@BindingAdapter("nombre")
fun setNombre(textView: TextView, nombre: String) {
    textView.text = nombre
}

@BindingAdapter("precio")
fun setPrecio(textView: TextView, precio: Double) {
    textView.text = precio.toString()
}
