package com.example.recetario.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.recetario.clases.Receta

@Dao
interface RecetaDao {
    @Query("SELECT * FROM recetas WHERE tipoReceta = :tipo AND estadoReceta = 'Activo'")
    fun getAll(tipo: String): LiveData<List<Receta>>

    @Query("SELECT * FROM recetas WHERE idReceta = :id AND estadoReceta = 'Activo'")
    fun get(id: Int): LiveData<Receta>

    @Insert
    fun insertAll(vararg receta: Receta)

    @Update
    fun update(receta: Receta)

    @Query("SELECT MAX(idReceta) FROM recetas")
    fun getNumeroRecetas(): Int
}