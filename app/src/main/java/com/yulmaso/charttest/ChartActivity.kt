package com.yulmaso.charttest

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yulmaso.charttest.chartView.ChartValue
import com.yulmaso.charttest.chartView.CountersChartView
import java.util.*

/**
 *  Created by yulmaso
 *  Date: 15.04.21
 */
class ChartActivity: AppCompatActivity() {

    private val data = listOf<ChartValue>(
        ChartValue(20f, Calendar.getInstance().also { it.set(2020, 0, 21) }),
        ChartValue(0f, Calendar.getInstance().also { it.set(2020, 2, 21) }),
        ChartValue(202f, Calendar.getInstance().also { it.set(2019, 5, 21) }),
        ChartValue(400f, Calendar.getInstance().also { it.set(2022, 8, 21) }),
        ChartValue(88f, Calendar.getInstance().also { it.set(2020, 11, 21) }),
        ChartValue(100f, Calendar.getInstance().also { it.set(2020, 10, 21) }),
        ChartValue(202f, Calendar.getInstance().also { it.set(2021, 3, 21) }),
        ChartValue(202f, Calendar.getInstance().also { it.set(2019, 2, 21) }),
        ChartValue(29f,  Calendar.getInstance().also { it.set(2020, 7, 21) }),
        ChartValue(58f,  Calendar.getInstance().also { it.set(2021, 7, 21) }),
        ChartValue(88f,  Calendar.getInstance().also { it.set(2020, 7, 21) }),
        ChartValue(100f, Calendar.getInstance().also { it.set(2020, 1, 21) }),
        ChartValue(20f,  Calendar.getInstance().also { it.set(2020, 3, 21) }),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        findViewById<CountersChartView>(R.id.chart).setData(
            data,
            Resources.getSystem().displayMetrics.widthPixels
        )

    }

}