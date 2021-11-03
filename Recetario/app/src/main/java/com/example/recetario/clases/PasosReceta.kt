package com.example.recetario.clases

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pasosReceta")
class PasosReceta (val idReceta: Int,
                   val descripcionPaso: String,
                   val estadoPaso: String,
                   @PrimaryKey(autoGenerate = true)
                   var idPasosReceta: Int = 0): Serializable