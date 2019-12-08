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
import android.os.Handler
import org.jetbrains.anko.longToast

const val QUESTION_REQUEST = 1
const val CAMERA_REQUEST = 2
const val REQUEST_IMAGE_CAPTURE = 3
const val OPERATION_REQUEST = 4
const val SENSOR_REQUEST = 5

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.setTextColor(Color.parseColor("#42dee1"))

        reto1.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto2.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto3.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto4.setBackgroundColor(Color.parseColor("#ff8ba7"))

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

    override fun onResume() {
        super.onResume()

        if(!reto1.isEnabled && !reto2.isEnabled && !reto3.isEnabled && !reto4.isEnabled) {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("Enhorabuena!")
            alertDialog.setMessage("Ganaste el juego. Quieres jugar otra vez?")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Si") { dialog, _ ->
                volverAJugar()
            }

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, _ ->
                dialog.run {
                    finish()
                }
            }
            alertDialog.show()
        }
    }


    fun volverAJugar() {
        reto1.isEnabled = true
        reto2.isEnabled = true
        reto3.isEnabled = true
        reto4.isEnabled = true

        reto1.setImageResource(R.drawable.yellowstar)
        reto2.setImageResource(R.drawable.yellowstar)
        reto3.setImageResource(R.drawable.yellowstar)
        reto4.setImageResource(R.drawable.yellowstar)

        reto1.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto2.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto3.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto4.setBackgroundColor(Color.parseColor("#ff8ba7"))
    }

    fun mostrarReto1() {
        val questionIntent = Intent(this, Reto1Activity::class.java)

        startActivityForResult(questionIntent, QUESTION_REQUEST)
    }

    fun mostrarReto2() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)
        }

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

        startActivityForResult(operationIntent, OPERATION_REQUEST)
    }

    fun mostrarReto4() {
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), SENSOR_REQUEST)
        }
        val miIntent = Intent(this, Reto4Activity::class.java)

        val alertDialog = AlertDialog.Builder(this).create()

        alertDialog.setTitle("Nota")
        alertDialog.setMessage("En este reto tienes que activar el sensor de proximidad o el giroscopio.")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") {
                dialog, _ -> dialog.dismiss()
        }
        alertDialog.show()
        alertDialog.setOnDismissListener {
            startActivityForResult(miIntent, SENSOR_REQUEST)
        }


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
                            longToast("Reto 3 superado! El resultado es " + data.getIntExtra("resultado", 0).toString())
                            reto3.isEnabled = false
                            reto3.setImageResource(R.drawable.colorstar)
                            reto3.setBackgroundColor(Color.parseColor("#c3f0ca"))
                        }
                    Activity.RESULT_CANCELED ->
                        Log.d("miApp", "Cancelado")
                }
            SENSOR_REQUEST ->
                when(resultCode) {
                    Activity.RESULT_OK ->
                        if(data != null) {
                            toast("Reto 4 superado!")
                            reto4.isEnabled = false
                            reto4.setImageResource(R.drawable.colorstar)
                            reto4.setBackgroundColor(Color.parseColor("#c3f0ca"))
                        }
                    Activity.RESULT_CANCELED ->
                        Log.d("miApp", "Cancelado")
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CAMERA_REQUEST ->
            {
                when(grantResults[0]) {
                    PackageManager.PERMISSION_GRANTED ->
                        toast("camera permission granted")
                    PackageManager.PERMISSION_DENIED ->
                        toast("camera permission denied. The app needs permissions to run.")
                }
            }
        }
    }
}
