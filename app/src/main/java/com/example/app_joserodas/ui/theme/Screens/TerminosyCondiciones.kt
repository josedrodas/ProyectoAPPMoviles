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
fun TerminosScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            Row(
                Modifier.fillMaxWidth().background(Color(0xFFDE4954))
                    .statusBarsPadding().height(56.dp).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("←", color = Color.White, fontSize = 22.sp,
                    modifier = Modifier.clickable { onBack() }.padding(end = 12.dp))
                Text("Términos y condiciones", color = Color.White, fontSize = 18.sp)
            }
        }
    ) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Texto de términos y condiciones.")
        }
    }
}
