package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_joserodas.model.Libro

@Composable
fun ProductoScreen(
    idLibro: Int,
    getLibro: (Int) -> Libro?,
    onAddToCart: (Libro) -> Unit,
    onBack: () -> Unit
) {
    val libro = remember(idLibro) { getLibro(idLibro) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (libro == null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Libro no encontrado",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(16.dp))
                    TextButton(onClick = onBack) {
                        Text(
                            "Volver",
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            return
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = libro.imagenRes),
                        contentDescription = libro.titulo,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(300.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    libro.titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "por ${libro.autor.nombre} ${libro.autor.apellido}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "$${libro.precio}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDE4954)
                )

                Spacer(Modifier.height(16.dp))

                HorizontalDivider(color = Color(0xFFE0E0E0))

                Spacer(Modifier.height(16.dp))

                Text(
                    "Descripción",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    libro.descripcion,
                    fontSize = 14.sp,
                    color = Color(0xFF444444),
                    lineHeight = 18.sp
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = { onAddToCart(libro) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDE4954),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Agregar al carrito",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(12.dp))

                TextButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Volver",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
