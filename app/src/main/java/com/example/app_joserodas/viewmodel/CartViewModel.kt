package com.example.app_joserodas.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.app_joserodas.model.Libro

class CartViewModel : ViewModel() {

    // estado interno del carrito
    private val _carrito = mutableStateListOf<Libro>()
    val carrito: List<Libro> get() = _carrito

    // descuento aplicado
    var descuentoAplicado: Int = 0
        private set

    // códigos válidos
    private val codigosDescuento = mapOf(
        "LIBRO10" to 10,
        "LECTOR15" to 15,
        "PALABRAS20" to 20,
        "RADIANTES25" to 25
    )

    fun aplicarDescuento(codigo: String): String {
        val codigoLimpio = codigo.trim().uppercase()
        return when {
            codigoLimpio.isEmpty() -> "Ingresa un código de descuento"
            codigosDescuento.containsKey(codigoLimpio) -> {
                descuentoAplicado = codigosDescuento[codigoLimpio] ?: 0
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

    fun obtenerCantidadTotal(): Int = _carrito.size

    fun obtenerTotalCarrito(): Int = _carrito.sumOf { it.precio }

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
