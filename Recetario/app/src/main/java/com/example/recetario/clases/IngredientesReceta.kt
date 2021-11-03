package com.example.recetario.clases

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ingredientesReceta")
class IngredientesReceta (val idReceta: Int,
                          val nombreIngrediente: String,
                          val cantidadIngrediente: String,
                          val tipoUnidad: String,
                          val estadoIngrediente: String,
                          @PrimaryKey(autoGenerate = true)
                          var idIngredientesReceta: Int = 0): Serializable