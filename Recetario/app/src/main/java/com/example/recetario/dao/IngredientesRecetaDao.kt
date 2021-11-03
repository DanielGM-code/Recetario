package com.example.recetario.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.recetario.clases.IngredientesReceta
import com.example.recetario.clases.PasosReceta

@Dao
interface IngredientesRecetaDao {
    @Query("SELECT * FROM ingredientesReceta WHERE estadoIngrediente = 'Activo'")
    fun getAll(): LiveData<List<IngredientesReceta>>

    @Query("SELECT * FROM ingredientesReceta WHERE idReceta = :id AND estadoIngrediente = 'Activo'")
    fun getByIdReceta(id: Int): LiveData<List<IngredientesReceta>>

    @Insert
    fun insertAll(vararg ingredienteReceta: IngredientesReceta)

    @Update
    fun update(ingredienteReceta: IngredientesReceta)
}