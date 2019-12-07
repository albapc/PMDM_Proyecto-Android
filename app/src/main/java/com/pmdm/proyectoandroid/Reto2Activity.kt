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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reto2)

        val data = Intent()

        val imageBitmap = intent.extras!!.get("image") as Bitmap //con !! se le dice que es seguro que no es nulo
        imageView.setImageBitmap(imageBitmap)
        toast("Lo conseguiste! Reto 2 superado")

        /*

        val extras = intent.extras
        if (extras != null) {
            val imageBitmap = extras.get("image") as Bitmap //con !! se le dice que es seguro que no es nulo
            if (imageBitmap != null) {
                imageView.setImageBitmap(imageBitmap)
            }
        }

         */

        bVolver.setOnClickListener {view ->


                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
    }
