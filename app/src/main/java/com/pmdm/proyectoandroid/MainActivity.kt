package com.pmdm.proyectoandroid

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import android.graphics.Bitmap

const val QUESTION_REQUEST = 1
const val CAMERA_REQUEST = 2
const val REQUEST_IMAGE_CAPTURE = 3
const val OPERATION_REQUEST = 4

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.setTextColor(Color.parseColor("#42dee1"))

        reto1.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto2.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto3.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto4.setBackgroundColor(Color.parseColor("#ff8ba7"))


        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)
        }

        reto1.setOnClickListener {
            mostrarReto1()
        }

        reto2?.setOnClickListener {
            mostrarReto2()
        }

        reto3.setOnClickListener {
            mostrarReto3()
        }

        reto4.setOnClickListener {
            mostrarReto4()
        }
    }


    fun mostrarReto1() {
        val questionIntent = Intent(this, Reto1Activity::class.java)

        startActivityForResult(questionIntent, QUESTION_REQUEST)

    }

    fun mostrarReto2() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val alertDialog = AlertDialog.Builder(this).create()

        alertDialog.setTitle("Nota")
        alertDialog.setMessage("En este reto tienes que sacar una foto y que se muestre en miniatura.")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") {
                dialog, _ -> dialog.dismiss()
        }
        alertDialog.show()
        alertDialog.setOnDismissListener {

            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    fun mostrarReto3() {
        val operationIntent = Intent(this, Reto3Activity::class.java)


        startActivity(operationIntent)
    }

    fun mostrarReto4() {
        val miIntent = Intent(this, Reto1Activity::class.java)

        //miIntent.putExtra("color", "rojo")

        startActivity(miIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            QUESTION_REQUEST ->
                when (resultCode) {
                    Activity.RESULT_OK ->
                        if (data != null) {
                            reto1.isEnabled = false
                            reto1.setImageResource(R.drawable.colorstar)
                            reto1.setBackgroundColor(Color.parseColor("#c3f0ca"))
                        }
                    Activity.RESULT_CANCELED ->
                        Log.d("miApp", "Cancelado")
                }
            REQUEST_IMAGE_CAPTURE ->
                when(resultCode) {
                    Activity.RESULT_OK ->
                        if(data != null) {
                            val thumbnail = data.extras?.get("data") as Bitmap

                            val intent = Intent(this, Reto2Activity::class.java)
                            intent.putExtra("image", thumbnail)
                            startActivity(intent)

                            reto2.isEnabled = false
                            reto2.setImageResource(R.drawable.colorstar)
                            reto2.setBackgroundColor(Color.parseColor("#c3f0ca"))
                        }
                    Activity.RESULT_CANCELED ->
                        Log.d("miApp", "Cancelado")
                }
            OPERATION_REQUEST ->
                when(resultCode) {
                    Activity.RESULT_OK ->
                        if(data != null) {

                            reto3.isEnabled = false
                            reto4.setImageResource(R.drawable.colorstar)
                            reto1.setBackgroundColor(Color.parseColor("#c3f0ca"))
                        }
                }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toast("camera permission granted")
            } else {
                toast("camera permission denied")
            }
        }
    }
}