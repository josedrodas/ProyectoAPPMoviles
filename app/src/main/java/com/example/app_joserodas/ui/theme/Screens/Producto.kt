package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_joserodas.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun ProductoScreen(
    viewModel: MainViewModel,
    idLibro: Int,
    onBack: () -> Unit
) {
    val libro = viewModel.getProductoPorId(idLibro)
    var mostrarMensaje by remember { mutableStateOf(false) }

    LaunchedEffect(mostrarMensaje) {
        if (mostrarMensaje) {
            delay(2000)
            mostrarMensaje = false
        }
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color(0xFFDE4954))) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("←",
                        color = Color.White,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .clickable { onBack() }
                            .padding(end = 12.dp)
                    )
                    Text("Detalle del Producto",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (libro != null) {
                Image(
                    painter = painterResource(id = libro.imagenRes),
                    contentDescription = "Portada de ${libro.titulo}",
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(300.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    libro.titulo,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Autor: ${libro.autor.nombre} ${libro.autor.apellido}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    "Editorial: ${libro.editorial.nombre}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    libro.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "$${libro.precio}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (mostrarMensaje) {
                    Text(
                        "✓ ¡Agregado al carrito!",
                        color = Color.Green,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Button(
                    onClick = {
                        viewModel.agregarAlCarrito(libro)
                        mostrarMensaje = true
                    }
                ) {
                    Text("Agregar al Carrito")
                }
            } else {
                Text("Producto no encontrado", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text("Volver al Inicio")
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}