package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recetario.adapter.RecetaAdapter
import com.example.recetario.clases.Receta
import androidx.lifecycle.Observer
import com.example.recetario.database.AppDataBase
import kotlinx.android.synthetic.main.activity_favoritos.*

class Favoritos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        var listaFavoritos = emptyList<Receta>()
        val database = AppDataBase.getDataBase(this)

        database.recetas().getAllFavoritos().observe(this, Observer {

            listaFavoritos = it
            val adapterListaRecetas = RecetaAdapter(this, listaFavoritos)
            listViewListaFavoritos.adapter = adapterListaRecetas
        })

        listViewListaFavoritos.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, ResumenReceta::class.java)
            intent.putExtra("Receta", listaFavoritos[position])
            startActivity(intent)
        }
    }
}