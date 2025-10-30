package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_joserodas.viewmodel.CartViewModel
import com.example.app_joserodas.viewmodel.CatalogViewModel
import kotlinx.coroutines.delay

@Composable
fun ProductoScreen(
    catalogVM: CatalogViewModel,
    cartVM: CartViewModel,
    idLibro: Int,
    onBack: () -> Unit
) {
    val libro = catalogVM.getProductoPorId(idLibro)
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
                    Text(
                        "←",
                        color = Color.White,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .clickable { onBack() }
                            .padding(end = 12.dp)
                    )
                    Text(
                        "Detalle del Producto",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Autor: ${libro.autor.nombre} ${libro.autor.apellido}",
                    fontSize = 16.sp
                )

                Text(
                    "Editorial: ${libro.editorial.nombre}",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    libro.descripcion,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "$${libro.precio}",
                    fontSize = 20.sp,
                    color = Color(0xFFDE4954),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (mostrarMensaje) {
                    Text(
                        "✓ ¡Agregado al carrito!",
                        color = Color.Green,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Button(
                    onClick = {
                        cartVM.agregarAlCarrito(libro)
                        mostrarMensaje = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954)),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text("Agregar al Carrito", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text("Volver al Inicio", fontSize = 16.sp)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Producto no encontrado", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954))
                    ) {
                        Text("Volver al Inicio")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
