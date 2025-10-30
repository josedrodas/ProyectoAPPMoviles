package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_joserodas.model.Libro
import com.example.app_joserodas.viewmodel.CartViewModel
import kotlinx.coroutines.delay

@Composable
fun CarritoScreen(
    cartVM: CartViewModel,
    onBack: () -> Unit,
    onProcederPago: (Int) -> Unit
) {
    val carrito = cartVM.carrito

    var codigoInput by remember { mutableStateOf("") }
    var mensajeDescuento by remember { mutableStateOf("") }
    var mostrarMensaje by remember { mutableStateOf(false) }

    val total = cartVM.obtenerTotalCarrito()
    val totalConDescuento = cartVM.obtenerTotalConDescuento()
    val ahorro = cartVM.obtenerAhorro()
    val descuentoAplicado = cartVM.descuentoAplicado

    LaunchedEffect(mostrarMensaje) {
        if (mostrarMensaje) {
            delay(3000)
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
                        "Mi Carrito",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "${cartVM.obtenerCantidadTotal()} items",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        if (carrito.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Carrito vacío", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Tu carrito está vacío",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Agrega algunos libros",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954))
                ) {
                    Text("Seguir Comprando")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(carrito) { libro ->
                        ItemCarrito(
                            libro = libro,
                            onEliminar = { cartVM.eliminarDelCarrito(libro) }
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Código de Descuento",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = codigoInput,
                                onValueChange = { codigoInput = it },
                                placeholder = { Text("Ej: LIBRO10, LECTOR15") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    val resultado = cartVM.aplicarDescuento(codigoInput)
                                    mensajeDescuento = resultado
                                    mostrarMensaje = true
                                    codigoInput = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954))
                            ) {
                                Text("Aplicar")
                            }
                        }

                        if (mostrarMensaje) {
                            Text(
                                text = mensajeDescuento,
                                color = if (descuentoAplicado > 0) Color.Green else Color.Red,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        if (descuentoAplicado > 0) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Descuento aplicado:")
                                Text(
                                    text = "${descuentoAplicado}%",
                                    color = Color.Green,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Button(
                                onClick = {
                                    cartVM.limpiarDescuento()
                                    mostrarMensaje = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                            ) {
                                Text("Quitar descuento")
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Resumen de Compra",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal:")
                            Text("$$total")
                        }

                        if (descuentoAplicado > 0) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Descuento (${descuentoAplicado}%):")
                                Text(
                                    text = "-$$ahorro",
                                    color = Color.Green,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$$totalConDescuento",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFDE4954)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                onProcederPago(totalConDescuento)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954))
                        ) {
                            Text("Proceder al Pago")
                        }

                        Button(
                            onClick = { cartVM.limpiarCarrito() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Vaciar Carrito")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCarrito(
    libro: Libro,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = libro.imagenRes),
                contentDescription = "Portada de ${libro.titulo}",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = libro.titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${libro.autor.nombre} ${libro.autor.apellido}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${libro.precio}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDE4954)
                )
            }

            IconButton(onClick = onEliminar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        }
    }
}
