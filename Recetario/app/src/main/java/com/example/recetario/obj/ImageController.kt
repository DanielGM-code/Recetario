package com.example.recetario.obj

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File

object ImageController {
    fun seleccionarFotoDeGaleria(activity: Activity, code: Int){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, code)
    }

    fun guardarImagen(context: Context, id: Long, uri: Uri){
        val archivo = File(context.filesDir, id.toString())

        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()!!

        archivo.writeBytes(bytes)
    }

    fun getImageUri(context: Context, id: Long): Uri{
        var archivo = File(context.filesDir, id.toString())

        return if(archivo.exists()) Uri.fromFile(archivo)
        else Uri.parse("android.resource://com.example.recetario/drawable/ic_baseline_image_24")
    }
}