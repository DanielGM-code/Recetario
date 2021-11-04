package com.example.recetario

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.recetario.clases.IngredientesReceta
import com.example.recetario.clases.PasosReceta
import com.example.recetario.clases.Receta
import com.example.recetario.database.AppDataBase
import com.example.recetario.obj.ImageController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_registrar_receta.*
import kotlinx.android.synthetic.main.activity_resumen_receta.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResumenReceta : AppCompatActivity() {

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen_receta)

        val receta = intent.getSerializableExtra("Receta") as Receta

        var idReceta = receta.idReceta
        var listaIngredientes = emptyList<IngredientesReceta>()
        var listaPasos = emptyList<PasosReceta>()
        var listaCantidades = mutableListOf<Int>()
        var listaCantidadesAux = mutableListOf<Int>()

        val imageUri = ImageController.getImageUri(this, idReceta.toLong())

        val database = AppDataBase.getDataBase(this)

        imageViewReceta.setImageURI(imageUri)
        textViewNombreReceta.text = receta.nombreReceta
        textViewNumPersonas.text = "Cantidad: ${receta.numPersonas} personas"
        imageViewValoracion.setImageResource(receta.valoracionReceta)
        textViewDificultadReceta.text = receta.dificultadReceta
        textViewDuracionReceta.text = "${receta.duracionReceta} minutos"

        database.ingredientes().getByIdReceta(idReceta).observe(this, Observer {
            listaIngredientes= it

            llenarTablaIngredientes(listaIngredientes, listaCantidades, listaCantidadesAux)
        })

        database.pasos().getByIdReceta(idReceta).observe(this, Observer {
            listaPasos = it

            llenarTablaPasos(listaPasos)
        })

        obtenerValorDelBotonDeFavorito(receta)

        val eliminarReceta = findViewById<FloatingActionButton>(R.id.botonEliminarReceta)
        eliminarReceta.setOnClickListener {

            AlertDialog.Builder(this).apply {
                setMessage("¿Estás seguro(a) de que quieres eliminar esta receta?")

                setPositiveButton("Sí"){ dialogInterface: DialogInterface, i: Int ->
                    val receta = Receta(receta.nombreReceta,
                        receta.numPersonas,
                        R.drawable.imagencomplementos,
                        "Inactivo",
                        receta.tipoReceta,
                        receta.valoracionReceta,
                        receta.dificultadReceta,
                        receta.duracionReceta,
                        receta.esFavorito)

                    receta.idReceta = idReceta

                    CoroutineScope(Dispatchers.IO).launch{
                        database.recetas().update(receta)

                        imageUri?.let {
                            ImageController.guardarImagen(this@ResumenReceta, receta.idReceta.toLong(), it)
                        }

                        this@ResumenReceta.finish()
                    }
                }

                setNegativeButton("No", null)
            }.show()
        }


        val agregarOQuitarDeFavoritos = findViewById<FloatingActionButton>(R.id.botonAgregarFavorito)
        agregarOQuitarDeFavoritos.setOnClickListener {
            if(receta.esFavorito == "noFavorito"){
                val receta = Receta(receta.nombreReceta,
                    receta.numPersonas,
                    R.drawable.imagencomplementos,
                    receta.estadoReceta,
                    receta.tipoReceta,
                    receta.valoracionReceta,
                    receta.dificultadReceta,
                    receta.duracionReceta,
                    "esFavorito")

                receta.idReceta = idReceta

                CoroutineScope(Dispatchers.IO).launch{
                    database.recetas().update(receta)

                    imageUri?.let {
                        ImageController.guardarImagen(this@ResumenReceta, receta.idReceta.toLong(), it)
                    }
                }
                obtenerValorDelBotonDeFavorito(receta)
            }else{
                val receta = Receta(receta.nombreReceta,
                    receta.numPersonas,
                    R.drawable.imagencomplementos,
                    receta.estadoReceta,
                    receta.tipoReceta,
                    receta.valoracionReceta,
                    receta.dificultadReceta,
                    receta.duracionReceta,
                    "noFavorito")

                receta.idReceta = idReceta

                CoroutineScope(Dispatchers.IO).launch{
                    database.recetas().update(receta)

                    imageUri?.let {
                        ImageController.guardarImagen(this@ResumenReceta, receta.idReceta.toLong(), it)
                    }
                }
                obtenerValorDelBotonDeFavorito(receta)
            }
        }

        val calcularIngredientes = findViewById<Button>(R.id.botonCalcularIngredientes)
        calcularIngredientes.setOnClickListener {
            if(validarCampoNumeroPersonas()){
                var layoutIngrediente = findViewById<LinearLayout>(R.id.layoutIngrediente)
                var layoutCantidad = findViewById<LinearLayout>(R.id.layoutCantidad)
                var layoutUnidad = findViewById<LinearLayout>(R.id.layoutUnidad)

                layoutIngrediente.removeAllViewsInLayout()
                layoutCantidad.removeAllViewsInLayout()
                layoutUnidad.removeAllViewsInLayout()

                calcularCantidades(listaCantidadesAux, listaCantidades, receta)

                llenarTablaIngredientes(listaIngredientes, listaCantidades, listaCantidadesAux)

                limpiarCampo()
            }else{
                Toast.makeText(this, "Ingrese una cantidad", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerCantidades(listaIngredientes: List<IngredientesReceta>, listaCantidades: MutableList<Int>, listaCantidadesAux: MutableList<Int>) {
        for(ingrediente in listaIngredientes){
            listaCantidades.add(ingrediente.cantidadIngrediente.toInt())
            listaCantidadesAux.add(ingrediente.cantidadIngrediente.toInt())
        }
    }


    private fun llenarTablaIngredientes(listaIngredientes: List<IngredientesReceta>, listaCantidades: MutableList<Int>, listaCantidadesAux: MutableList<Int>) {

        var layoutIngrediente = findViewById<LinearLayout>(R.id.layoutIngrediente)
        var layoutCantidad = findViewById<LinearLayout>(R.id.layoutCantidad)
        var layoutunidad = findViewById<LinearLayout>(R.id.layoutUnidad)
        var posicionCantidad = 0

        obtenerCantidades(listaIngredientes, listaCantidades, listaCantidadesAux)

        for(ingrediente in listaIngredientes){

            var textviewIngrediente = TextView(this)
            var textviewCantidad = TextView(this)
            var textviewUnidad = TextView(this)

            textviewIngrediente.setText(ingrediente.nombreIngrediente)
            textviewCantidad.setText(listaCantidadesAux[posicionCantidad].toString())
            textviewUnidad.setText(ingrediente.tipoUnidad)

            layoutIngrediente.addView(textviewIngrediente)
            layoutCantidad.addView(textviewCantidad)
            layoutunidad.addView(textviewUnidad)

            posicionCantidad += 1
        }
    }

    private fun llenarTablaPasos(listaPasos: List<PasosReceta>) {
        var layoutNumPaso = findViewById<LinearLayout>(R.id.layoutNumeroPaso)
        var layoutPaso = findViewById<LinearLayout>(R.id.layoutPaso)

        var numeroPaso = 0

        for(paso in listaPasos){
            numeroPaso += 1

            var textViewNumeroPaso = TextView(this)
            var textViewPaso = TextView(this)

            textViewNumeroPaso.setText(numeroPaso.toString())
            textViewPaso.setText(paso.descripcionPaso)

            layoutNumPaso.addView(textViewNumeroPaso)
            layoutPaso.addView(textViewPaso)
        }
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

    private fun obtenerValorDelBotonDeFavorito(receta: Receta){
        if(receta.esFavorito == "noFavorito"){
            botonAgregarFavorito.setImageResource(R.drawable.estrella_gris)
        }else{
            botonAgregarFavorito.setImageResource(R.drawable.estrella_amarilla)
        }
    }

    private fun validarCampoNumeroPersonas() : Boolean{
        return editTextNumeroPersonas.text.isNotEmpty()
    }

    private fun calcularCantidades(listaCantidadesAux: MutableList<Int>, listaCantidades: MutableList<Int>, receta: Receta){
        var numPersonas = editTextNumeroPersonas.text.toString().toInt()
        var numPersonasRecetaOriginal = receta.numPersonas.toInt()
        var numPersonasActual = numPersonas
        var posicionCantidad = 0

        for(cantidad in listaCantidades){
            var cantidadCalculada = cantidad * numPersonas / numPersonasRecetaOriginal

            listaCantidadesAux.set(posicionCantidad, cantidadCalculada)

            posicionCantidad += 1
        }

        numPersonasActual = numPersonas

        textViewNumPersonas.text = "Cantidad: ${numPersonasActual} personas"
    }

    private fun limpiarCampo(){
        editTextNumeroPersonas.setText("")
    }
}