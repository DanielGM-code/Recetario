package com.example.recetario.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.recetario.R
import com.example.recetario.clases.IngredientesReceta
import kotlinx.android.synthetic.main.item_ingrediente_receta.view.*

class IngredienteAdapter(private val mContext: Context, private val listaIngredientesReceta: List<IngredientesReceta>) : ArrayAdapter<IngredientesReceta>(mContext, 0, listaIngredientesReceta) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutIngrediente = LayoutInflater.from(mContext).inflate(R.layout.item_ingrediente_receta, parent, false)

        val ingrediente = listaIngredientesReceta[position]

        layoutIngrediente.textViewNombreIngrediente.text = ingrediente.nombreIngrediente
        layoutIngrediente.textViewCantidadIngrediente.text = ingrediente.cantidadIngrediente
        layoutIngrediente.textViewUnidadIngrediente.text = ingrediente.tipoUnidad

        return layoutIngrediente
    }
}