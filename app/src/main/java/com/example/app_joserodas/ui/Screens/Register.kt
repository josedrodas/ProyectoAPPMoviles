package com.example.app_joserodas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(onBack: () -> Unit, onRegistered: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Row(
                Modifier.fillMaxWidth().background(Color(0xFFDE4954))
                    .statusBarsPadding().height(56.dp).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("←", color = Color.White, fontSize = 22.sp,
                    modifier = Modifier.clickable { onBack() }.padding(end = 12.dp))
                Text("Registro", color = Color.White, fontSize = 18.sp)
            }
        }
    ) { inner ->
        Column(
            Modifier.padding(inner).padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(value = apellido, onValueChange = { apellido = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                if (nombre.isNotBlank() && apellido.isNotBlank() && email.isNotBlank() && pass.isNotBlank()) onRegistered()
            }, modifier = Modifier.fillMaxWidth()) { Text("Crear cuenta") }
        }
    }
}
