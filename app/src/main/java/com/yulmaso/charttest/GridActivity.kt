package com.yulmaso.charttest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yulmaso.charttest.chartView.ChartValue
import com.yulmaso.charttest.test.GridView
import java.util.*

/**
 *  Created by yulmaso
 *  Date: 21.04.21
 */
class GridActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)

        findViewById<GridView>(R.id.grid_view).setData(listOf(ChartValue(1f, Calendar.getInstance())))
    }
}