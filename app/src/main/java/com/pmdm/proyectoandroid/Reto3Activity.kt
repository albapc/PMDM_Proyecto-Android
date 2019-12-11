package com.pmdm.proyectoandroid

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_reto3.*
import org.jetbrains.anko.toast

class Reto3Activity : AppCompatActivity() {

    //variables globales que vamos a utilizar en varias funciones
    private var suma = false
    private var resta = false
    private var multiplicacion = false
    private var division = false
    private var resultado = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        //crea la activity y la muestra
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reto3)

        //cambia el color del texto
        tReto3.setTextColor(Color.parseColor("#baabda"))

        //variable de tipo intent
        val data = Intent()


        //cada boton lleva asignada una acción, el resultado de esta se almacena en una variable
        bSuma.setOnClickListener {
            resultado = hacerCalculo(it)
        }

        bResta.setOnClickListener {
            resultado = hacerCalculo(it)
        }

        bMultiplicacion.setOnClickListener {
            resultado = hacerCalculo(it)
        }

        bDivision.setOnClickListener {
            resultado = hacerCalculo(it)
        }

        bIgual.setOnClickListener {
            resultado = hacerCalculo(it)

            //espera unos segundos
            Handler().postDelayed(
                {
                    //crea el cuadro de dialogo
                    val alertDialog = AlertDialog.Builder(this).create()
                    alertDialog.setTitle("Enhorabuena!")
                    alertDialog.setMessage("Reto 3 superado! Quieres hacer otra operacion?")
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Si") { dialog, _ ->
                        dialog.dismiss() //si pulsamos si, cierra el cuadro
                    }

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, _ ->
                        dialog.run {
                            data.putExtra("resultado", resultado)
                            setResult(Activity.RESULT_OK, data)
                            finish() //si pulsamos no, cierra la activity
                        }
                    }
                    alertDialog.show()
                },
                1500 // value in milliseconds
            )
        }
    }

    private fun hacerCalculo(view: View): Int {

        //variables obtenidas de las cajas de texto, las pasamos a int
        val num1 = Integer.parseInt(tNum1.text.toString())
        val num2 = Integer.parseInt(tNum2.text.toString())

        //dependiendo de la id del botón pulsado (suma, resta, multiplicacion, division o igual)...
        when (view.id) {
            R.id.bSuma -> {
                suma = true
                resta = false
                multiplicacion = false
                division = false

            }
            R.id.bResta -> {
                suma = false
                resta = true
                multiplicacion = false
                division = false
            }

            R.id.bMultiplicacion -> {
                suma = false
                resta = false
                multiplicacion = true
                division = false
            }

            R.id.bDivision -> {
                suma = false
                resta = false
                multiplicacion = false
                division = true
            }
            R.id.bIgual -> {
                when {
                    suma -> {
                        resultado = num1 + num2
                    }
                    resta -> {
                        resultado = num1 - num2
                    }
                    multiplicacion -> {
                        resultado = num1 * num2
                    }
                    division -> {
                        resultado = num1 / num2
                    }
                }
                toast(Integer.toString(resultado)) //muestra el resultado en un toast
            }
        }
        return resultado //devuelve el resultado
    }
}











