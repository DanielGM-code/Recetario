package com.example.recetario.clases

import java.io.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
class Receta (val nombreReceta:String,
              val numPersonas: String,
              val imagenReceta: Int,
              val estadoReceta: String,
              val tipoReceta: String,
              val valoracionReceta: Int,
              val dificultadReceta: String,
              val duracionReceta: String,
              val esFavorito: String,
              @PrimaryKey(autoGenerate = true)
              var idReceta: Int = 0) : Serializable