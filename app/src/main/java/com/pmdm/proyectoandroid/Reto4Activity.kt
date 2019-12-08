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

    private var sensorManager: SensorManager? = null
    private var proximitySensor: Sensor? = null
    private var proximitySensorListener: SensorEventListener? = null
    private var gyroscopeSensorListener: SensorEventListener? = null
    private var gyroscopeSensor: Sensor? = null
    private var data = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reto4)

        probarProximidad()

        probarGiroscopio()
    }


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

        sensorManager?.unregisterListener(proximitySensorListener)
    }

    private fun probarProximidad() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        proximitySensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY) as Sensor

        if (proximitySensor == null) {
            Log.d("miApp", "Proximity sensor not available.")
            finish() // Cerrar app
        }

        // Crea el listener
        proximitySensorListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

            override fun onSensorChanged(sensorEvent: SensorEvent) {
                if (sensorEvent.values[0] < proximitySensor!!.maximumRange) {
                    // Detecto algo cerca
                    window.decorView.setBackgroundColor(Color.RED)

                    data.putExtra("conseguido", true)
                    setResult(Activity.RESULT_OK, data)
                    Handler().postDelayed(
                        {
                            finish()
                        }, 1500
                    )
                } else {
                    // No hay nada cerca
                    window.decorView.setBackgroundColor(Color.GREEN)
                }
            }
        }
    }

    private fun probarGiroscopio() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        gyroscopeSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE) as Sensor

        if (gyroscopeSensor == null) {
            Log.d("miApp", "Proximity sensor not available.")
            finish() // Cerrar app

        }

        // Create a listener
        gyroscopeSensorListener = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                if (sensorEvent.values[2] > 0.5f) { // anticlockwise
                    window.decorView.setBackgroundColor(Color.BLUE)
                    data.putExtra("conseguido", true)
                    setResult(Activity.RESULT_OK, data)
                    Handler().postDelayed(
                        {
                            finish()
                        }, 1500
                    )
                } else if (sensorEvent.values[2] < -0.5f) { // clockwise
                    window.decorView.setBackgroundColor(Color.YELLOW)
                    data.putExtra("conseguido", true)
                    setResult(Activity.RESULT_OK, data)
                    Handler().postDelayed(
                        {
                            finish()
                        }, 1500
                    )
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
        }
    }
}

