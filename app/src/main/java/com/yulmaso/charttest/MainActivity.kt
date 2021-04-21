package com.yulmaso.charttest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.yulmaso.charttest.test.TestActivity

class MainActivity : AppCompatActivity() {
    private val testData = listOf(
        Entry(1f, 2f),
        Entry(2f, 5f),
        Entry(3f, 2f),
        Entry(4f, 7f),
        Entry(5f, 5f),
        Entry(6f, 4f),
        Entry(7f, 3f),
        Entry(8f, 1f),
        Entry(9f, 2f),
        Entry(10f, 3f),
        Entry(11f, 6f),
        Entry(12f, 2f),
        Entry(13f, 5f),
        Entry(14f, 2f),
        Entry(15f, 7f),
        Entry(16f, 5f),
        Entry(17f, 4f),
        Entry(18f, 3f),
        Entry(19f, 1f),
        Entry(20f, 2f),
        Entry(21f, 3f),
        Entry(22f, 6f),
        Entry(23f, 2f),
        Entry(24f, 5f),
        Entry(25f, 2f),
        Entry(26f, 7f),
        Entry(27f, 5f),
        Entry(28f, 4f),
        Entry(29f, 3f),
        Entry(30f, 1f),
        Entry(31f, 2f),
        Entry(32f, 3f),
        Entry(33f, 6f),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lineChart = findViewById<LineChart>(R.id.lineChart)

        val dataSet = LineDataSet(testData, "TEST")
        dataSet.color = ContextCompat.getColor(this, R.color.purple_200)
        dataSet.valueTextColor = ContextCompat.getColor(this, R.color.purple_500)
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()

        findViewById<Button>(R.id.btn).setOnClickListener {
            Intent(this, ChartActivity::class.java).also {
                startActivity(it)
            }
        }

        findViewById<Button>(R.id.grid_btn).setOnClickListener {
            Intent(this, GridActivity::class.java).also {
                startActivity(it)
            }
        }

        findViewById<Button>(R.id.test_btn).setOnClickListener {
            Intent(this, TestActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}