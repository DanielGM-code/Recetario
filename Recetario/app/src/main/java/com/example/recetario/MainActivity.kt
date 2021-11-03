package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val EDIT_ACTIVITY = 49

        val abrirPlatillosPrincipales = findViewById<ImageButton>(R.id.botonPlatillosPrincipales)
        abrirPlatillosPrincipales.setOnClickListener{

            val intent = Intent(this, ListaRecetas::class.java)
            startActivity(intent.putExtra("tipo", "Principal"))
        }

        val abrirEntradas = findViewById<ImageButton>(R.id.botonEntradas)
        abrirEntradas.setOnClickListener{

            val intent = Intent(this, ListaRecetas::class.java)
            startActivity(intent.putExtra("tipo", "Entrada"))
        }

        val abrirPostres = findViewById<ImageButton>(R.id.botonPostres)
        abrirPostres.setOnClickListener{

            val intent = Intent(this, ListaRecetas::class.java)
            startActivity(intent.putExtra("tipo", "Postre"))
        }

        val abrirComplementos = findViewById<ImageButton>(R.id.botonComplementos)
        abrirComplementos.setOnClickListener{

            val intent = Intent(this, ListaRecetas::class.java)
            startActivity(intent.putExtra("tipo", "Complemento"))
        }

        /*val abrirFavoritos = findViewById<ImageButton>(R.id.botonFavoritos)
        abrirFavoritos.setOnClickListener{

            val intent = Intent(this, lista_recetas_favoritas::class.java)
            intent.putExtra("TipoReceta", true)
            startActivity(intent)
        }*/
    }
}