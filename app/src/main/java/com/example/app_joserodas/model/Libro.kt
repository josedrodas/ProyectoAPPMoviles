package com.example.app_joserodas.model

data class Libro(
    val idLibro: Int,
    val titulo: String,
    val autor: Autor,
    val editorial: Editorial,
    val precio: Int,
    val imagenRes: Int,
    val descripcion: String
)
