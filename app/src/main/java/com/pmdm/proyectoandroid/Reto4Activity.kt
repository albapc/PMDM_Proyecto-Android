package com.pmdm.proyectoandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class Reto4Activity : AppCompatActivity() {

    //variables globales para utilizar los sensores y el intent
    private var sensorManager: SensorManager? = null
    private var proximitySensor: Sensor? = null
    private var proximitySensorListener: SensorEventListener? = null
    private var gyroscopeSensorListener: SensorEventListener? = null
    private var gyroscopeSensor: Sensor? = null
    private var data = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        //crea la activity y la muestra
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reto4)

        //función sensor proximidad
        probarProximidad()

        //función girosccopio
        probarGiroscopio()
    }

    //reanuda la activity
    override fun onResume() {
        super.onResume()

        // Registra el intervalo de cambio en milisegundos
        sensorManager?.registerListener(
            proximitySensorListener,
            proximitySensor, 2 * 1000 * 1000

        )

        //Registra el listener
        sensorManager?.registerListener(
            gyroscopeSensorListener,
            gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()

        //anula el listener cuando la activity está onPause
        sensorManager?.unregisterListener(proximitySensorListener)
    }

    private fun probarProximidad() {
        //objecto sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //objeto sensor de proximidad
        proximitySensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY) as Sensor

        //si no se detecta ningún sensor...
        if (proximitySensor == null) {
            //muestra el mensaje en la consola
            Log.d("miApp", "Proximity sensor not available.")
            finish() // Cerrar app
        }

        // Crea el listener
        proximitySensorListener = object : SensorEventListener {
            //funcion que detecta los cambios en la precision (no se utiliza, pero es obligatorio incluirla)
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

            //funcion que detecta los cambios en el sensor
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                //si los valores que detectó el sensor son menores que el rango máximo que el sensor de proximidad...
                if (sensorEvent.values[0] < proximitySensor!!.maximumRange) {
                    // Detecto algo cerca
                    window.decorView.setBackgroundColor(Color.RED) //cambia el fondo a rojo

                    data.putExtra("conseguido", true) //devuelve un valor a la activity main
                    setResult(Activity.RESULT_OK, data) //devuelve un resultado a la activity main
                    //espera unos segundos para cerrar la activity
                    Handler().postDelayed(
                        {
                            finish()
                        }, 1500
                    )
                } else {
                    // No hay nada cerca
                    window.decorView.setBackgroundColor(Color.GREEN) //cambia el fondo a verde
                }
            }
        }
    }

    private fun probarGiroscopio() {
        //objecto sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //objeto giroscopio
        gyroscopeSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE) as Sensor

        if (gyroscopeSensor == null) {
            Log.d("miApp", "Gyroscope not available.")
            finish() // Cerrar app

        }

        // Crea un listener
        gyroscopeSensorListener = object : SensorEventListener {
            //funcion que detecta los cambios realizados en el sensor
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                //si los valores del sensor son mayores de 0.5....
                if (sensorEvent.values[2] > 0.5f) { // sentido contrario a las agujas del reloj
                    window.decorView.setBackgroundColor(Color.BLUE) //cambia el fondo a azul
                    //devuelve un valor a la activity main
                    data.putExtra("conseguido", true)
                    setResult(Activity.RESULT_OK, data)
                    //espera unos segundos para cerrar la activity
                    Handler().postDelayed(
                        {
                            finish()
                        }, 1500
                    )
                } else if (sensorEvent.values[2] < -0.5f) { // sentido de las agujas del reloj
                    window.decorView.setBackgroundColor(Color.YELLOW) //cambia el fondo a amarillo
                    //devuelve un valor a la activity main
                    data.putExtra("conseguido", true)
                    setResult(Activity.RESULT_OK, data)
                    //espera unos segundos para cerrar la activity
                    Handler().postDelayed(
                        {
                            finish()
                        }, 1500
                    )
                }
            }
            //funcion que detecta los cambios en la precision (no se utiliza, pero es obligatorio incluirla)
            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
        }
    }
}

