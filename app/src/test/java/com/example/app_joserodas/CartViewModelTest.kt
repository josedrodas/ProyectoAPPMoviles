package com.example.app_joserodas

import com.example.app_joserodas.model.Autor
import com.example.app_joserodas.model.Editorial
import com.example.app_joserodas.model.Libro
import com.example.app_joserodas.viewmodel.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val libroBarato = Libro(
        idLibro = "1",
        titulo = "Libro Barato",
        autor = Autor(1, "A", "A"),
        editorial = Editorial(1, "E", "E"),
        precio = 10000,
        imagenUrl = "http://fake.url",
        descripcion = "desc"
    )

    private val libroCaro = Libro(
        idLibro = "2",
        titulo = "Libro Caro",
        autor = Autor(2, "B", "B"),
        editorial = Editorial(2, "E", "E"),
        precio = 20000,
        imagenUrl = "http://fake.url",
        descripcion = "desc"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CartViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // test 1: Agregar al Carrito
    @Test
    fun `agregarAlCarrito aumenta el tamaño de la lista`() {
        viewModel.agregarAlCarrito(libroBarato)
        assertEquals(1, viewModel.obtenerCantidadTotal())
    }

    // test 2 Calcular Totales
    @Test
    fun `obtenerTotalCarrito suma correctamente los precios`() {
        viewModel.agregarAlCarrito(libroBarato) // 10.000
        viewModel.agregarAlCarrito(libroCaro)   // 20.000

        assertEquals(30000, viewModel.obtenerTotalCarrito())
    }

    // test 3 Aplicar Descuentos
    @Test
    fun `calculo final con descuento es correcto`() {
        viewModel.agregarAlCarrito(libroCaro) // 20.000

        viewModel.aplicarDescuento("PALABRAS20")

        assertEquals(16000, viewModel.obtenerTotalConDescuento())
    }
}