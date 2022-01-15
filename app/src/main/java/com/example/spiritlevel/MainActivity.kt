package com.example.spiritlevel

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(),SensorEventListener {
    lateinit var sensorManager: SensorManager
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView=findViewById(R.id.spirit)

        sensorManager=getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if(p0?.sensor?.type==Sensor.TYPE_ACCELEROMETER) {
            val sides=p0.values[0]
            val upDown=p0.values[1]

            textView.apply {
                rotationX=upDown*3f
                rotationY=sides*3f
                rotation=-sides
                translationX=sides*-10
                translationY=upDown*10
            }

            val color=if(sides.toInt()==0 && upDown.toInt()==0)Color.GREEN else Color.RED
            textView.setBackgroundColor(color)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
}