package com.example.app_joserodas.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EncuentranosScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val urlMapa = "https://www.google.com/maps/search/?api=1&query=-33.4163,-70.6064"

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDE4954))
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "←",
                    color = Color.White,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .clickable { onBack() }
                        .padding(end = 12.dp)
                )
                Text(
                    text = "Encuéntranos",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Sucursal principal",
                fontSize = 20.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Costanera Center\nNivel comercial\nAv. Andrés Bello 2447\nProvidencia, Santiago"
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Ver en el mapa",
                color = Color(0xFFDE4954),
                fontSize = 18.sp,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlMapa))
                    context.startActivity(intent)
                }
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Al abrir el mapa puedes ver tu ubicación y cómo llegar."
            )
        }
    }
}
