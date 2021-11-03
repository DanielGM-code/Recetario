package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.recetario.clases.Receta
import com.example.recetario.adapter.RecetaAdapter
import com.example.recetario.database.AppDataBase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_lista_recetas.*

class ListaRecetas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_recetas)

        val extra = intent.extras
        val tipoReceta = extra?.getString("tipo").toString()

        var listaRecetas = emptyList<Receta>()
        val database = AppDataBase.getDataBase(this)

        database.recetas().getAll(tipoReceta).observe(this, Observer {

            listaRecetas = it
            val adapterListaRecetas = RecetaAdapter(this, listaRecetas)
            listViewListaRecetas.adapter = adapterListaRecetas
        })


        listViewListaRecetas.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, ResumenReceta::class.java)
            intent.putExtra("Receta", listaRecetas[position])
            startActivity(intent)
        }

        val abrirRegistrarReceta = findViewById<FloatingActionButton>(R.id.botonAgregarReceta)
        abrirRegistrarReceta.setOnClickListener{

            val intent = Intent(this, RegistrarReceta::class.java)
            startActivity(intent.putExtra("tipo", tipoReceta))
        }

    }
}