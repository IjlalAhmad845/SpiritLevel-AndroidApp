package com.example.spiritlevel

import android.content.res.ColorStateList
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var fab1: FloatingActionButton
    private lateinit var fab2: FloatingActionButton

    private lateinit var textView1: TextView
    private lateinit var textView2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab1 = findViewById(R.id.spirit)
        fab2 = findViewById(R.id.spirit2)

        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
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
        if (p0?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val leftRight = p0.values[0]
            val upDown = p0.values[1]

            fab1.apply {
                rotationX = upDown * 2f
                rotationY = leftRight * 2f
                rotation = -leftRight
                translationX = leftRight * -3
                translationY = upDown * 3
            }

            fab2.apply {
                translationX = leftRight * -30
                translationY = upDown * 30
            }

            val color =
                if (leftRight.toInt() == 0 && upDown.toInt() == 0) Color.GREEN else Color.RED

            fab2.backgroundTintList = ColorStateList.valueOf(color)

            when {
                leftRight.toInt() < 0 -> textView1.text = getString(R.string.text_horizontal_msg,"Tilted RIGHT")
                leftRight.toInt() > 0 -> textView1.text = getString(R.string.text_horizontal_msg,"Tilted LEFT")
                else -> textView1.text = getString(R.string.text_horizontal_msg,"BALANCED")
            }

            when {
                upDown.toInt() < 0 -> textView2.text = getString(R.string.text_vertical_msg,"Tilted UP")
                upDown.toInt() > 0 -> textView2.text = getString(R.string.text_vertical_msg,"Tilted DOWN")
                else -> textView2.text = getString(R.string.text_vertical_msg,"BALANCED")
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
}