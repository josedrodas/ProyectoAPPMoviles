package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.app_joserodas.viewmodel.MainViewModel

@Composable
fun LoginScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onGoRegister: () -> Unit, // queda sin uso, pero lo mantenemos por firma
    onLogged: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val emailOk = email.trim().contains("@") && email.contains(".")
    val passOk = pass.length >= 8
    val formOk = emailOk && passOk

    Column(Modifier.padding(16.dp)) {
        Text("Iniciar sesión", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") },
            isError = email.isNotEmpty() && !emailOk,
            supportingText = { if (email.isNotEmpty() && !emailOk) Text("Email inválido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = pass, onValueChange = { pass = it },
            label = { Text("Contraseña") },
            isError = pass.isNotEmpty() && !passOk,
            supportingText = { if (pass.isNotEmpty() && !passOk) Text("Mínimo 8 caracteres") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        if (errorMsg != null) {
            Text(errorMsg!!, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                errorMsg = null
                val result = viewModel.login(email, pass)
                result.onSuccess { onLogged() }
                    .onFailure { e -> errorMsg = e.message ?: "Credenciales inválidas" }
            },
            enabled = formOk,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Entrar") }

        TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }

        // Si quieres mostrar ayudas:
        Spacer(Modifier.height(24.dp))
        Text("Usuarios de prueba:", style = MaterialTheme.typography.titleMedium)
        Text("• admin@palabras.com / Admin1234")
        Text("• usuario@palabras.com / User1234")
    }
}
