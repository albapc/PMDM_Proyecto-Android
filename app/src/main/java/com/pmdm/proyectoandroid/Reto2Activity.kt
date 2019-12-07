package com.pmdm.proyectoandroid

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reto2.*
import org.jetbrains.anko.toast


class Reto2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //crea la activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reto2)

        //variable que almacenara el intent que usaremos
        val data = Intent()

        //variable de tipo bitmap que almacenara el intent con la etiqueta image
        val imageBitmap =
            intent.extras!!.get("image") as Bitmap //con !! se le dice que es seguro que no es nulo

        //almacena dicha imagen en un elemento de tipo imageView de la interfaz, para que podamos verla
        imageView.setImageBitmap(imageBitmap)

        //mensaje generado
        toast("Lo conseguiste! Reto 2 superado")

        //accion realizada al pulsar el boton
        bVolver.setOnClickListener { view ->
            //devuelve un resultado positivo y almacena los datos
            setResult(Activity.RESULT_OK, data)
            //cierra esta activity
            finish()
        }
    }
}
