package com.example.app_joserodas

import com.example.app_joserodas.model.OpenLibraryDoc
import com.example.app_joserodas.model.OpenLibraryResponse
import com.example.app_joserodas.viewmodel.BooksApiService
import com.example.app_joserodas.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val apiMock = mockk<BooksApiService>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //  test 4 Funcionalidad de Búsqueda Exitosa
    @Test
    fun `buscarProductos actualiza la lista cuando la API responde bien`() = runTest(testDispatcher) {
        val docsFalsos = listOf(OpenLibraryDoc("key1", "Harry Potter", null, 123, null, null))
        val respuestaExitosa = OpenLibraryResponse(docs = docsFalsos)

        coEvery { apiMock.searchBooks(any()) } returns respuestaExitosa

        val viewModel = HomeViewModel(apiMock)

        viewModel.buscarProductos("Harry")
        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertEquals(1, estado.productos.size)
        assertEquals("Harry Potter", estado.productos[0].titulo)
    }

    // test 5 Funcionalidad de Error en Búsqueda
    @Test
    fun `buscarProductos muestra mensaje de error si la API falla`() = runTest(testDispatcher) {
        coEvery { apiMock.searchBooks(any()) } throws RuntimeException("Error de conexión")

        val viewModel = HomeViewModel(apiMock)

        viewModel.buscarProductos("Algo")
        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue(estado.errorMsg != null)
    }
}