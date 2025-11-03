package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_joserodas.viewmodel.AuthViewModel

@Composable
fun PerfilScreen(
    viewModel: AuthViewModel,
    onBack: () -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi Perfil")

        Spacer(Modifier.height(16.dp))

        if (currentUser == null) {
            Text("No has iniciado sesión.")
        } else {
            Text("Nombre: ${currentUser?.nombre}")
            Text("Email: ${currentUser?.email}")
            Text("Rol: ${currentUser?.rol}")
        }

        Spacer(Modifier.height(24.dp))

        if (currentUser != null) {
            Button(onClick = { viewModel.logout() }) {
                Text("Cerrar sesión")
            }
            Spacer(Modifier.height(8.dp))
        }

        TextButton(onClick = onBack) {
            Text("Volver")
        }
    }
}
