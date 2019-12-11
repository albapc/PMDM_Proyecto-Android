package com.pmdm.proyectoandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reto1.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.toast


class Reto1Activity : AppCompatActivity() {

    //declaramos los arrays con las preguntas y sus respectivas respuestas al inicio para tener
    //acceso a ellas desde todos los metodos que utilicemos

    private val questions = arrayOf( //array que contiene las preguntas
        "¿En qué año nació Benjamin Franklin?",
        "¿Cuántos años tiene Arturo Pérez Reverte?",
        "¿En qué ciudad está el museo del Prado?"
    )

    //array que contiene las respuestas correctas
    private val correctAnswer = arrayOf("1706", "68", "Madrid")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reto1)

        //genera un numero aleatorio, este numero se ampliará o se reducirá conforme el número de preguntas que haya
        val r = (0..2).random()

        //se asigna ese numero aleatorio al indice del array y muestra ese valor en el texto
        tPregunta.setText(questions[r])

        //al pulsar "enviar", se llama al metodo que decide si una respuesta es correcta o no
        bEnviar.setOnClickListener {
            validarPreguntas()
        }

        //al pulsar el botón de "ayuda", se llama al metodo que abre el navegador con la página que indiquemos
        bAyuda.setOnClickListener {
            buscarAyuda()
        }
    }

    fun validarPreguntas() {

        val data = Intent()

        //analiza la pregunta que se muestra
        when (tPregunta.text.toString()) {
            //segun la posicion del array donde estuviera la pregunta...
            questions[0] ->
                //si la respuesta introducida es igual a la respuesta correcta de esa pregunta...
                if (tRespuesta.text.toString() == correctAnswer[0]) {
                    //muestra el mensaje
                    toast("Correcto! Ruta 1 superada")
                    //devuelve un valor al main
                    data.putExtra("correcto", true)
                    //cambia el resultado del activity para que ejecute el onActivityResult
                    setResult(Activity.RESULT_OK, data)
                    //cierra esta activity
                    finish()
                } else { //de lo contrario...
                    toast("Oooooh... No es correcto, vuelve a intentarlo") //muestra el siguiente mensaje
                }
            questions[1] ->
                if (tRespuesta.text.toString() == correctAnswer[1]) {
                    toast("Correcto! Ruta 1 superada")
                    data.putExtra("correcto", true)
                    setResult(Activity.RESULT_OK, data)
                    finish()
                } else {
                    toast("Oooooh... No es correcto, vuelve a intentarlo")
                }

            questions[2] ->
                //en este caso, al ser una respuesta no numérica, comparamos ambos strings, ignorando
                //mayúsculas y minúsculas
                if (tRespuesta.text.toString().equals(correctAnswer[2], ignoreCase = true)) {
                    toast("Correcto! Ruta 1 superada")
                    data.putExtra("correcto", true)
                    setResult(Activity.RESULT_OK, data)
                    finish()
                } else {
                    toast("Oooooh... No es correcto, vuelve a intentarlo")
                }
        }
    }

    fun buscarAyuda() {

        //dependiendo de la pregunta mostrada...
        when (tPregunta.text.toString()) {
            questions[0] ->
                //muestra un enlace u otro
                browse("https://es.wikipedia.org/wiki/Benjamin_Franklin")

            questions[1] ->
                browse("https://es.wikipedia.org/wiki/Arturo_P%C3%A9rez-Reverte")

            questions[2] ->
                browse("https://es.wikipedia.org/wiki/Museo_del_Prado")
        }
    }
}
