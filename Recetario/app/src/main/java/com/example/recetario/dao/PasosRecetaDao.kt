package com.example.recetario.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.recetario.clases.PasosReceta

@Dao
interface PasosRecetaDao {
    @Query("SELECT * FROM pasosReceta WHERE estadoPaso = 'Activo'")
    fun getAll(): LiveData<List<PasosReceta>>

    @Query("SELECT * FROM pasosReceta WHERE idReceta = :id AND estadoPaso = 'Activo'")
    fun getByIdReceta(id: Int): LiveData<List<PasosReceta>>

    @Insert
    fun insertAll(vararg pasosReceta: PasosReceta)

    @Update
    fun update(pasosReceta: PasosReceta)
}