package com.example.recetario.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recetario.clases.IngredientesReceta
import com.example.recetario.clases.PasosReceta
import com.example.recetario.clases.Receta
import com.example.recetario.dao.IngredientesRecetaDao
import com.example.recetario.dao.PasosRecetaDao
import com.example.recetario.dao.RecetaDao

@Database(entities = [Receta::class, IngredientesReceta::class, PasosReceta::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun recetas(): RecetaDao
    abstract fun ingredientes(): IngredientesRecetaDao
    abstract fun pasos(): PasosRecetaDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}