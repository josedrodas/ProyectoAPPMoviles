package com.example.app_joserodas.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.app_joserodas.model.Libro

class CartViewModel : ViewModel() {

    private val _carrito = mutableStateListOf<Libro>()
    val carrito: List<Libro> get() = _carrito

    var descuentoAplicado: Int = 0
        private set

    private val codigosDescuento = mapOf(
        "LIBRO10" to 10,
        "LECTOR15" to 15,
        "PALABRAS20" to 20,
        "RADIANTES25" to 25
    )

    fun agregarAlCarrito(libro: Libro) {
        _carrito.add(libro)
    }

    fun eliminarDelCarrito(libro: Libro) {
        _carrito.remove(libro)
    }

    fun limpiarCarrito() {
        _carrito.clear()
        limpiarDescuento()
    }

    fun obtenerCantidadTotal(): Int {
        return _carrito.size
    }

    fun obtenerTotalCarrito(): Int {
        return _carrito.sumOf { it.precio }
    }

    fun aplicarDescuento(codigo: String): String {
        val limpio = codigo.trim().uppercase()
        return when {
            limpio.isEmpty() -> "Ingresa un código de descuento"
            codigosDescuento.containsKey(limpio) -> {
                descuentoAplicado = codigosDescuento[limpio] ?: 0
                "¡Descuento del $descuentoAplicado% aplicado!"
            }
            else -> {
                descuentoAplicado = 0
                "Código inválido o expirado"
            }
        }
    }

    fun limpiarDescuento() {
        descuentoAplicado = 0
    }

    fun obtenerAhorro(): Int {
        val total = obtenerTotalCarrito()
        return (total * descuentoAplicado) / 100
    }

    fun obtenerTotalConDescuento(): Int {
        val total = obtenerTotalCarrito()
        val descuento = (total * descuentoAplicado) / 100
        return total - descuento
    }
}
