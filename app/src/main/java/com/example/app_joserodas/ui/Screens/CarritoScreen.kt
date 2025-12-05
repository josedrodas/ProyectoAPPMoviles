package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage // <--- IMPORTANTE: Importamos Coil
import com.example.app_joserodas.R
import com.example.app_joserodas.model.Libro
import com.example.app_joserodas.viewmodel.CartViewModel

@Composable
fun CarritoScreen(
    viewModel: CartViewModel,
    onBack: () -> Unit,
    onProcederPago: () -> Unit
) {
    val carrito: List<Libro> = viewModel.carrito
    var codigo by remember { mutableStateOf("") }
    var mensajeCodigo by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tu Carrito",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000)
            )
            Spacer(Modifier.weight(1f))

            Box(modifier = Modifier.size(44.dp), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.carrito),
                    contentDescription = "Carrito",
                    modifier = Modifier.size(26.dp),
                    contentScale = ContentScale.Fit
                )
                val cant = viewModel.obtenerCantidadTotal()
                if (cant > 0) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(Color.Red, shape = CircleShape)
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(cant.toString(), color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .weight(1f, fill = false)
            ) {
                items(carrito) { libro ->
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // --- CAMBIO AQUÍ: Usamos AsyncImage en lugar de Image ---
                            AsyncImage(
                                model = libro.imagenUrl, // URL de internet
                                contentDescription = libro.titulo,
                                modifier = Modifier.size(60.dp),
                                contentScale = ContentScale.Crop,
                                // Placeholder mientras carga
                                placeholder = painterResource(R.drawable.logo_palabras_radiantes),
                                error = painterResource(R.drawable.logo_palabras_radiantes)
                            )

                            Spacer(Modifier.size(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = libro.titulo,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 2
                                )
                                Text(
                                    text = "por ${libro.autor.nombre}",
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "$${libro.precio}",
                                    fontSize = 14.sp,
                                    color = Color(0xFFDE4954),
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.size(12.dp))

                            IconButton(onClick = { viewModel.eliminarDelCarrito(libro) }) {
                                Text("✕", color = Color(0xFFDE4954), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        HorizontalDivider(color = Color(0xFFE0E0E0))
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código de descuento") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { mensajeCodigo = viewModel.aplicarDescuento(codigo) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954), contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Aplicar código", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        if (mensajeCodigo != null) {
            Spacer(Modifier.height(8.dp))
            Text(mensajeCodigo!!, fontSize = 14.sp, color = Color(0xFF333333))
        }

        Spacer(Modifier.height(16.dp))

        val subtotal = viewModel.obtenerTotalCarrito()
        val ahorro = viewModel.obtenerAhorro()
        val totalConDesc = viewModel.obtenerTotalConDescuento()

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Subtotal:", fontSize = 14.sp, color = Color.Gray)
                    Text("$$subtotal", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Descuento:", fontSize = 14.sp, color = Color(0xFFDE4954))
                    Text("-$$ahorro", fontSize = 14.sp, color = Color(0xFFDE4954))
                }
                Spacer(Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total a pagar:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("$$totalConDesc", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDE4954))
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onProcederPago,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954), contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Proceder al pago", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(Modifier.height(12.dp))
        TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver", fontSize = 15.sp, color = Color.Gray)
        }
    }
}