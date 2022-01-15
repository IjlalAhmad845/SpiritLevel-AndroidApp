package com.example.spiritlevel

import android.R.attr
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.R.attr.button
import android.content.res.ColorStateList

import android.graphics.drawable.Drawable




class MainActivity : AppCompatActivity(),SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var fab1: FloatingActionButton
    private lateinit var fab2: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab1=findViewById(R.id.spirit)
        fab2=findViewById(R.id.spirit2)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

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

            fab1.apply {
                rotationX=upDown*2f
                rotationY=sides*2f
                rotation=-sides
                translationX=sides*-3
                translationY=upDown*3
            }

            fab2.apply {
                translationX=sides*-30
                translationY=upDown*30
            }

            val color=if(sides.toInt()==0 && upDown.toInt()==0)Color.GREEN else Color.RED

            fab2.backgroundTintList= ColorStateList.valueOf(color)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
}