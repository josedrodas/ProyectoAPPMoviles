package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreguntasScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            Row(
                Modifier.fillMaxWidth().background(Color(0xFFDE4954))
                    .statusBarsPadding().height(56.dp).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("←", color = Color.White, fontSize = 22.sp,
                    modifier = Modifier.clickable { onBack() }.padding(end = 12.dp))
                Text("Preguntas frecuentes", color = Color.White, fontSize = 18.sp)
            }
        }
    ) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("FAQ — Palabras Radiantes\n" +
                    "\n" +
                    "1) ¿Envían a todo Chile?\n" +
                    "Sí, por [Courier]. Retiro gratis en [dirección].\n" +
                    "\n" +
                    "2) ¿Cuánto demora?\n" +
                    "RM: 24–72 h hábiles. Regiones: 2–6 días.\n" +
                    "\n" +
                    "3) ¿Cuánto cuesta el envío?\n" +
                    "Se calcula al pagar.\n" +
                    "\n" +
                    "4) ¿Qué medios de pago aceptan?\n" +
                    "Débito/crédito, transferencia y [otros].\n" +
                    "\n" +
                    "5) ¿Cómo sigo mi pedido?\n" +
                    "Te llega un tracking por correo.\n" +
                    "\n" +
                    "6) ¿Cambios/Devoluciones?\n" +
                    "10 días (sin uso). Fallas: garantía legal 6 meses.\n" +
                    "\n" +
                    "7) ¿Factura?\n" +
                    "Sí, selecciónala al pagar.\n" +
                    "\n" +
                    "8) ¿Libro agotado o a pedido?\n" +
                    "Reservamos y te avisamos fecha estimada.\n" +
                    "\n" +
                    "9) ¿Atención al cliente?\n" +
                    "[correo] · [WhatsApp] · Lun–Vie [horario].\n" +
                    "\n")
        }
    }
}
