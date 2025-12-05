package com.example.app_joserodas.model

data class Libro(
    val idLibro: String,
    val titulo: String,
    val autor: Autor,
    val editorial: Editorial,
    val precio: Int,
    val imagenUrl: String?,
    val descripcion: String
)