package com.example.recetario

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Informacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion)

        var urlInstagram = "https://www.instagram.com/danielgm.exe/"
        var urlGitHub = "https://github.com/DanielGM-code"

        var botonInstagram = findViewById<ImageButton>(R.id.botonInstagram)
        var botonGitHub = findViewById<ImageButton>(R.id.botonGitHub)

        botonInstagram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlInstagram))
            startActivity(intent)
        }

        botonGitHub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlGitHub))
            startActivity(intent)
        }
    }
}