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
import org.jetbrains.anko.longToast

//códigos de los requests de las distintas activities
const val QUESTION_REQUEST = 1
const val CAMERA_REQUEST = 2
const val REQUEST_IMAGE_CAPTURE = 3
const val OPERATION_REQUEST = 4
const val SENSOR_REQUEST = 5

class MainActivity : AppCompatActivity() {

    //función que inicia la activity principal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //cambia el color del texto
        textView.setTextColor(Color.parseColor("#42dee1"))

        //cambia el color de fondo de los botones
        reto1.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto2.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto3.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto4.setBackgroundColor(Color.parseColor("#ff8ba7"))

        //funcion que ejecutarán los botones al pulsarlos
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

    //función que se ejecuta cuando se reanuda la activity
    override fun onResume() {
        super.onResume()

        //si los cuatro botones están desactivados, es decir que todos los retos se superaron...
        if (!reto1.isEnabled && !reto2.isEnabled && !reto3.isEnabled && !reto4.isEnabled) {
            //creamos un cuadro de aviso
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("Enhorabuena!")
            alertDialog.setMessage("Ganaste el juego. Quieres jugar otra vez?")
            //opciones si/no
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Si") { _, _ ->
                volverAJugar() //si pulsamos "si" reinicia el juego
            }

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, _ ->
                dialog.run {
                    finish() //si pulsamos "no", cierra la app
                }
            }
            alertDialog.show() //lo muestra
        }
    }

    fun volverAJugar() {
        //reactiva los botones
        reto1.isEnabled = true
        reto2.isEnabled = true
        reto3.isEnabled = true
        reto4.isEnabled = true

        //restaura la imagen anterior, antes de superar cada nivel
        reto1.setImageResource(R.drawable.yellowstar)
        reto2.setImageResource(R.drawable.yellowstar)
        reto3.setImageResource(R.drawable.yellowstar)
        reto4.setImageResource(R.drawable.yellowstar)

        //restaura el color de fondo anterior
        reto1.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto2.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto3.setBackgroundColor(Color.parseColor("#ff8ba7"))
        reto4.setBackgroundColor(Color.parseColor("#ff8ba7"))
    }

    //reto 1: consiste en resolver una de las 3 preguntas que se generarán aleatoriamente, si el usuario
    //no sabe la respuesta, con pulsar el botón de ayuda se le abrirá el navegador con una página que contenga
    //la respuesta a la pregunta
    fun mostrarReto1() {
        //instanciamos la variable de tipo Intent y enlazamos con la activity que queremos mostrar
        val questionIntent = Intent(this, Reto1Activity::class.java)

        //inicia la activity y su request para que devuelva un resultado
        startActivityForResult(questionIntent, QUESTION_REQUEST)
    }

    //reto 2: consiste en hacer una foto con la cámara (para ello el usuario debe otorgar permiso a la app,
    // sino esta se cerrará y no podrá acceder a este reto, al resto sí), y que se muestre en miniatura
    fun mostrarReto2() {
        //si no tenemos permisos para utilizar la cámara...
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //le pide permiso al usuario
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)
        }

        //variables del intent de la cámara y el cuadro de aviso
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val alertDialog = AlertDialog.Builder(this).create()

        //contenido del cuadro de diálogo
        alertDialog.setTitle("Nota")
        alertDialog.setMessage("En este reto tienes que sacar una foto y que se muestre en miniatura.")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ ->
            dialog.dismiss() //si pulsa "ok" se cierra el cuadro
        }
        alertDialog.show() //lo muestra al usuario

        //indica que ocurrirá al cerrarse el cuadro de diálogo
        alertDialog.setOnDismissListener {
            //inicia la activity que abre la camara
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    //reto 3: consiste en realizar una operación matemática (suma, resta, multiplicación o división)
    //en la que el usuario debe introducir 2 números, pulsar una de las operaciones y después el botón
    //"igual"
    fun mostrarReto3() {
        //declara la variable de intent asociada a una activity
        val operationIntent = Intent(this, Reto3Activity::class.java)

        startActivityForResult(operationIntent, OPERATION_REQUEST) //inicia la activity
    }

    //reto 4: consiste en hacer uso del sensor del móvil (normalmente situado en la zona del altavoz,
    //que es el que hace que se apague la pantalla cuando acercamos el móvil a nuestra oreja para hablar
    //o escuchar un audio). también vale usar el giroscopio, girando el móvil hacia la izquierda o la derecha.
    //Si usamos el giroscopio, la pantalla cambiará a azul o amarillo dependiendo del lado hacia el que lo
    //giremos. Si usamos el sensor de proximidad, la pantalla debería pasar de verde a rojo. Si se hizo
    //correctamente, saltará un mensaje, finalizará la activity y volverá al menú principal
    fun mostrarReto4() {
        //declara la variable de intent asociada a una activity
        val miIntent = Intent(this, Reto4Activity::class.java)

        //crea un cuadro de dialogo
        val alertDialog = AlertDialog.Builder(this).create()

        //contenido del cuadro
        alertDialog.setTitle("Nota")
        alertDialog.setMessage("En este reto tienes que activar el sensor de proximidad o el giroscopio.")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ ->
            dialog.dismiss() //si pulsas "ok" cierra el cuadro
        }
        alertDialog.show() //lo inicia
        //tras cerra el cuadro inicia la activity
        alertDialog.setOnDismissListener {
            startActivityForResult(miIntent, SENSOR_REQUEST)
        }
    }

    //realiza ciertas acciones según el código de request y recibe los datos que queramos de la activity
    //que tenga asignada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //cuando el codigo sea...
        when (requestCode) {
            //codigo reto 1
            QUESTION_REQUEST ->
                when (resultCode) { //cuando el resultado de la activity sea...
                    Activity.RESULT_OK ->
                        //si devuelve algún dato...
                        if (data != null) {
                            //desactiva el botón del reto 1, cambia la imagen y el color del fondo
                            reto1.isEnabled = false
                            reto1.setImageResource(R.drawable.colorstar)
                            reto1.setBackgroundColor(Color.parseColor("#c3f0ca"))
                        }
                    Activity.RESULT_CANCELED ->
                        Log.d("miApp", "Cancelado") //muestra el siguiente mensaje en la consola
                }
            //codigo reto 2
            REQUEST_IMAGE_CAPTURE ->
                when (resultCode) {
                    Activity.RESULT_OK ->
                        if (data != null) {
                            //obtiene la foto hecha y la almacena como Bitmap
                            val thumbnail = data.extras?.get("data") as Bitmap

                            //llama al intent de la activity 2
                            val intent = Intent(this, Reto2Activity::class.java)
                            //le agrega la imagen con su codigo
                            intent.putExtra("image", thumbnail)
                            //inicia la activity
                            startActivity(intent)

                            //al volver de la activity desactiva el boton, cambia la imagen y el color de fondo
                            reto2.isEnabled = false
                            reto2.setImageResource(R.drawable.colorstar)
                            reto2.setBackgroundColor(Color.parseColor("#c3f0ca"))
                        }
                    Activity.RESULT_CANCELED ->
                        Log.d("miApp", "Cancelado") //muestra el siguiente mensaje en la consola
                }
            //codigo reto 3
            OPERATION_REQUEST ->
                when (resultCode) {
                    Activity.RESULT_OK ->
                        if (data != null) {
                            //devuelve el siguiente mensaje y recoge el resultado de la operacion hecha
                            //en la otra activity
                            longToast(
                                "Reto 3 superado! El resultado es " + data.getIntExtra(
                                    "resultado",
                                    0
                                ).toString()
                            )
                            //desactiva el boton, cambia la imagen y el color de fondo
                            reto3.isEnabled = false
                            reto3.setImageResource(R.drawable.colorstar)
                            reto3.setBackgroundColor(Color.parseColor("#c3f0ca"))
                        }
                    Activity.RESULT_CANCELED ->
                        Log.d("miApp", "Cancelado") //muestra el siguiente mensaje en la consola
                }
            //codigo reto 4
            SENSOR_REQUEST ->
                when (resultCode) {
                    Activity.RESULT_OK ->
                        if (data != null) {
                            toast("Reto 4 superado!")
                            //desactiva el boton, cambia la imagen y el color de fondo
                            reto4.isEnabled = false
                            reto4.setImageResource(R.drawable.colorstar)
                            reto4.setBackgroundColor(Color.parseColor("#c3f0ca"))
                        }
                    Activity.RESULT_CANCELED ->
                        Log.d("miApp", "Cancelado") //muestra el siguiente mensaje en la consola
                }
        }
    }

    //funcion que gestiona los permisos de la app
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        //hereda de la funcion con el mismo nombre
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //cuando el codigo sea...
        when (requestCode) {
            CAMERA_REQUEST -> {
                when (grantResults[0]) {
                    PackageManager.PERMISSION_GRANTED -> //si tiene permisos...
                        toast("camera permission granted")
                    PackageManager.PERMISSION_DENIED -> //si no los tiene...
                        toast("camera permission denied. The app needs permissions to run.")
                }
            }
        }
    }
}
