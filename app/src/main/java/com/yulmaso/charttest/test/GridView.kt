package com.yulmaso.charttest.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import com.yulmaso.charttest.chartView.ChartValue
import com.yulmaso.charttest.chartView.ChartView

/**
 *  Created by yulmaso
 *  Date: 21.04.21
 */
class GridView(context: Context, attributeSet: AttributeSet): ChartView<ChartValue>(context, attributeSet) {
    override fun drawChart(canvas: Canvas, data: List<ChartValue>, sectionWidth: Int) {
        drawGrid(canvas, RectF(0f, 0f, width.toFloat(), height.toFloat()), 20f)
    }
}