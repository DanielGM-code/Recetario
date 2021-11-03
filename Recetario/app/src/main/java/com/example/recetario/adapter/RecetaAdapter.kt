package com.example.recetario.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.recetario.R
import com.example.recetario.clases.IngredientesReceta
import com.example.recetario.clases.Receta
import com.example.recetario.obj.ImageController
import kotlinx.android.synthetic.main.item_receta.view.*

class RecetaAdapter(private val mContext: Context, private val listaRecetas: List<Receta>) : ArrayAdapter<Receta>(mContext, 0, listaRecetas){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutReceta = LayoutInflater.from(mContext).inflate(R.layout.item_receta, parent, false)

        val receta = listaRecetas[position]


        val imageUri = ImageController.getImageUri(mContext, receta.idReceta.toLong())

        layoutReceta.imageViewReceta.setImageURI(imageUri)
        layoutReceta.textViewNombreReceta.text = receta.nombreReceta
        layoutReceta.textViewNumPersonas.text = "Cantidad: ${receta.numPersonas} personas"
        layoutReceta.imageViewValoracion.setImageResource(receta.valoracionReceta)
        layoutReceta.textViewDificultadReceta.text = receta.dificultadReceta
        layoutReceta.textViewDuracionReceta.text = "${receta.duracionReceta} minutos"



        return layoutReceta
    }
}