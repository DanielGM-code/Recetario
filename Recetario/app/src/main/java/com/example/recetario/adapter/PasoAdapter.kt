package com.example.recetario.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.recetario.R
import com.example.recetario.clases.IngredientesReceta
import com.example.recetario.clases.PasosReceta
import kotlinx.android.synthetic.main.item_ingrediente_receta.view.*
import kotlinx.android.synthetic.main.item_ingrediente_receta.view.textViewCantidadIngrediente
import kotlinx.android.synthetic.main.item_paso_receta.view.*

class PasoAdapter(private val mContext: Context, private val listaPasosReceta: List<PasosReceta>) : ArrayAdapter<PasosReceta>(mContext, 0, listaPasosReceta) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutPaso = LayoutInflater.from(mContext).inflate(R.layout.item_paso_receta, parent, false)

        val paso = listaPasosReceta[position]
        var numeroPaso = ""

        layoutPaso.textViewNumPaso.text = numeroPaso
        layoutPaso.textViewDescripcionPaso.text = paso.descripcionPaso

        return layoutPaso
    }
}