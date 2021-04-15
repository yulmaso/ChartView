package com.yulmaso.charttest.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.yulmaso.charttest.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        
        val x = findViewById<EditText>(R.id.x_et)
        val x1 = findViewById<EditText>(R.id.x1_et)
        val x2 = findViewById<EditText>(R.id.x2_et)
        val x3 = findViewById<EditText>(R.id.x3_et)

        val y = findViewById<EditText>(R.id.y_et)
        val y1 = findViewById<EditText>(R.id.y1_et)
        val y2 = findViewById<EditText>(R.id.y2_et)
        val y3 = findViewById<EditText>(R.id.y3_et)

        val cv = findViewById<CurveView>(R.id.cv)

        findViewById<Button>(R.id.btn).setOnClickListener {
            cv.setData(
                x.text.toString().toFloat(),
                y.text.toString().toFloat(),
                x1.text.toString().toFloat(),
                y1.text.toString().toFloat(),
                x2.text.toString().toFloat(),
                y2.text.toString().toFloat(),
                x3.text.toString().toFloat(),
                y3.text.toString().toFloat(),
            )
        }
    }
}