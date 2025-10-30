package com.example.app_joserodas.ui.screens

import android.graphics.Bitmap
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

@Composable
fun PagoQRScreen(
    totalAPagar: Int,
    onPagoConfirmado: () -> Unit,
    onBack: () -> Unit
) {
    val qrData = "https://palabrasradiantes.local/pago-ok?monto=$totalAPagar"

    val qrBitmap = remember(qrData) {
        generarQRBitmap(qrData, 600, 600)
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
                            .padding(end = 12.dp)
                            .clickable { onBack() }
                    )
                    Text(
                        "Pagar con QR",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "$$totalAPagar CLP",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Escanea este código con tu teléfono para pagar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .size(260.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (qrBitmap != null) {
                        Image(
                            bitmap = qrBitmap.asImageBitmap(),
                            contentDescription = "QR de pago",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("Error creando QR")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Total a pagar:",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                "$$totalAPagar CLP",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFDE4954)
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { onPagoConfirmado() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE4954))
            ) {
                Text("Ya pagué", fontSize = 16.sp)
            }
        }
    }
}

private fun generarQRBitmap(data: String, width: Int, height: Int): Bitmap? {
    return try {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            data,
            BarcodeFormat.QR_CODE,
            width,
            height,
            null
        )
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(
                    x,
                    y,
                    if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
                )
            }
        }
        bmp
    } catch (e: Exception) {
        null
    }
}
