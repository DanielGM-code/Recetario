package com.example.recetario

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.recetario.clases.IngredientesReceta
import com.example.recetario.clases.PasosReceta
import com.example.recetario.clases.Receta
import com.example.recetario.database.AppDataBase
import kotlinx.android.synthetic.main.activity_registrar_receta.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.recetario.adapter.IngredienteAdapter
import com.example.recetario.adapter.PasoAdapter
import com.example.recetario.obj.ImageController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_lista_recetas.*
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast


class RegistrarReceta : AppCompatActivity() {

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_receta)

        val extra = intent.extras
        val tipoReceta = extra?.getString("tipo").toString()

        val arrayIngredientes: MutableList<IngredientesReceta> = mutableListOf()
        val arrayPasos: MutableList<PasosReceta> = mutableListOf()
        var idReceta = 0

        //spiner
        llenarSpiners()

        //Base de datos
        val database = AppDataBase.getDataBase(this)
        CoroutineScope(Dispatchers.IO).launch {
            idReceta = database.recetas().getNumeroRecetas() + 1
        }

        val registrarReceta = findViewById<FloatingActionButton>(R.id.botonGuardarReceta)
        registrarReceta.setOnClickListener{

            val nombre = editTextNombreReceta.text.toString()
            val numPersonas = editTextNumPersonas.text.toString().toInt()
            val imagenReceta = R.drawable.imagencomplementos
            val estadoReceta = "Activo"
            val calificacion = spinnerCalificacion.selectedItem.toString()
            var imagenCalificacion = R.drawable.una_estrella

            when(calificacion){
                "Una estrella" -> imagenCalificacion = R.drawable.una_estrella
                "Dos estrellas" -> imagenCalificacion = R.drawable.dos_estrella
                "Tres estrellas" -> imagenCalificacion = R.drawable.tres_estrella
                "Cuatro estrellas" -> imagenCalificacion = R.drawable.cuatro_estrella
                "Cinco estrellas" -> imagenCalificacion = R.drawable.cinco_estrella
            }
            val dificultad = spinnerDificultad.selectedItem.toString()
            val duracion = editTextDuracion.text.toString().toInt()

            val receta = Receta(nombre,
                numPersonas,
                imagenReceta,
                estadoReceta,
                tipoReceta,
                imagenCalificacion,
                dificultad,
                duracion)

            CoroutineScope(Dispatchers.IO).launch {
                database.recetas().insertAll(receta)

                for(ingrediente in arrayIngredientes){
                    database.ingredientes().insertAll(ingrediente)
                }

                for(paso in arrayPasos){
                    database.pasos().insertAll(paso)
                }

                imageUri?.let {
                    ImageController.guardarImagen(this@RegistrarReceta, idReceta.toLong(), it)
                }

                this@RegistrarReceta.finish()
            }
        }

        val agregarIngrediente = findViewById<Button>(R.id.botonAgregarIngrediente)
        agregarIngrediente.setOnClickListener{

            val nombreIngrediente = editTextIngrediente.text.toString()
            val cantidadIngrdiente = editTextCantidad.text.toString()
            val unidadIngrediente = spinnerUnidad.selectedItem.toString()

            val ingrediente = IngredientesReceta(idReceta,
                nombreIngrediente,
                cantidadIngrdiente,
                unidadIngrediente,
            "Activo")

            arrayIngredientes.add(ingrediente)

            llenarTablaIngredientes(arrayIngredientes)

            limpiarCamposIngredientes()
        }

        val agregarPaso = findViewById<Button>(R.id.botonAgregarPaso)
        agregarPaso.setOnClickListener{

            val descripcionPaso = editTextPaso.text.toString()

            val paso = PasosReceta(idReceta,
                descripcionPaso,
                "Activo")

            arrayPasos.add(paso)

            llenarTablaPasos(arrayPasos)

            limpiarCamposPasos()
        }

        imageViewImagenSeleccionada.setOnClickListener {
            ImageController.seleccionarFotoDeGaleria(this, SELECT_ACTIVITY)
        }

        val quitarIngrediente = findViewById<Button>(R.id.botonQuitarIngrediente)
        quitarIngrediente.setOnClickListener {
            arrayIngredientes.removeLast()
            llenarTablaIngredientes(arrayIngredientes)
        }

        val quitarPaso = findViewById<Button>(R.id.botonQuitarPaso)
        quitarPaso.setOnClickListener {
            arrayPasos.removeLast()
            llenarTablaPasos(arrayPasos)
        }
    }



    private fun llenarTablaIngredientes(arrayIngredientes: MutableList<IngredientesReceta>){
        var layoutIngrediente = findViewById<LinearLayout>(R.id.layoutIngrediente)
        var layoutCantidad = findViewById<LinearLayout>(R.id.layoutCantidad)
        var layoutUnidad = findViewById<LinearLayout>(R.id.layoutUnidad)

        layoutIngrediente.removeAllViewsInLayout()
        layoutCantidad.removeAllViewsInLayout()
        layoutUnidad.removeAllViewsInLayout()

        for(ingrediente in arrayIngredientes){
            var textviewIngrediente = TextView(this)
            var textviewCantidad = TextView(this)
            var textviewUnidad = TextView(this)

            textviewIngrediente.setText(ingrediente.nombreIngrediente)
            textviewCantidad.setText(ingrediente.cantidadIngrediente)
            textviewUnidad.setText(ingrediente.tipoUnidad)

            layoutIngrediente.addView(textviewIngrediente)
            layoutCantidad.addView(textviewCantidad)
            layoutUnidad.addView(textviewUnidad)
        }
    }

    private fun llenarTablaPasos(arrayPasos: MutableList<PasosReceta>) {
        var layoutNumeroPaso = findViewById<LinearLayout>(R.id.layoutNumeroPaso)
        var layoutPaso = findViewById<LinearLayout>(R.id.layoutDescripcion)

        layoutNumeroPaso.removeAllViewsInLayout()
        layoutPaso.removeAllViewsInLayout()

        var contadorPaso = 0

        for(paso in arrayPasos){
            contadorPaso += 1

            var textViewNumeroPaso = TextView(this)
            var textViewPaso = TextView(this)

            textViewNumeroPaso.setText(contadorPaso.toString())
            textViewPaso.setText(paso.descripcionPaso)

            layoutNumeroPaso.addView(textViewNumeroPaso)
            layoutPaso.addView(textViewPaso)
        }

    }

    private fun llenarSpiners(){
        val spinnerUnidad = findViewById<Spinner>(R.id.spinnerUnidad)
        val listaUnidad = arrayOf("gr", "ml", "cda", "cdita", "tz", "pz")
        val adaptadorUnidad = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaUnidad)

        val spinnerCalificacion = findViewById<Spinner>(R.id.spinnerCalificacion)
        val listaCalificacion = arrayOf("Una estrella", "Dos estrellas", "Tres estrellas", "Cuatro estrellas", "Cinco estrellas")
        val adaptadorCalificacion = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCalificacion)

        val spinnerDificultad = findViewById<Spinner>(R.id.spinnerDificultad)
        val listaDificultad = arrayOf("Fácil", "Medio", "Difícil")
        val adaptadorDificultad = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDificultad)

        spinnerUnidad.adapter = adaptadorUnidad
        spinnerCalificacion.adapter = adaptadorCalificacion
        spinnerDificultad.adapter = adaptadorDificultad
    }

    private fun limpiarCamposIngredientes(){
        editTextIngrediente.setText("")
        editTextCantidad.setText("")
    }

    private fun limpiarCamposPasos(){
        editTextPaso.setText("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when{
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK ->{
                imageUri = data!!.data

                imageViewImagenSeleccionada.setImageURI(imageUri)
            }
        }
    }
}