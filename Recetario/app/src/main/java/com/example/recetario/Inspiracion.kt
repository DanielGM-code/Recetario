package com.example.recetario

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Inspiracion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspiracion)

        var urlLaCapital = "https://www.youtube.com/c/lacapitalcocina"
        var urlMisPastelitos = "https://www.youtube.com/c/mispastelitosyoutub"
        var urlDeMiRancho = "https://www.youtube.com/c/DemiRanchoaTuCocina"
        var urlCocinaVegan = "https://www.youtube.com/c/CocinaVeganf%C3%A1cil"
        var urlCocinaYuta = "https://www.youtube.com/c/CocinaJaponesaconYuta"

        var botonVisitarLaCapital = findViewById<Button>(R.id.botonVisitarLaCapital)
        var botonVisitarMisPastelitos = findViewById<Button>(R.id.botonVisitarMisPastelitos)
        var botonVisitarDeMiRancho = findViewById<Button>(R.id.botonVisitarDeMiRancho)
        var botonVisitarCocinaVegan = findViewById<Button>(R.id.botonVisitarCocinaVegan)
        var botonVisitarCocinaYuta = findViewById<Button>(R.id.botonVisitarCocinaYuta)

        botonVisitarLaCapital.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlLaCapital))
            startActivity(intent)
        }

        botonVisitarMisPastelitos.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlMisPastelitos))
            startActivity(intent)
        }

        botonVisitarDeMiRancho.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlDeMiRancho))
            startActivity(intent)
        }

        botonVisitarCocinaVegan.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlCocinaVegan))
            startActivity(intent)
        }

        botonVisitarCocinaYuta.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlCocinaYuta))
            startActivity(intent)
        }
    }
}