package com.yulmaso.charttest.chartView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import com.yulmaso.charttest.dpToPx

/**
 *  Created by yulmaso
 *  Date: 15.04.21
 */
class CountersChartView(context: Context, attrs: AttributeSet): ChartView<ChartValue>(context, attrs) {

    companion object {
        private val TEXT_SIZE = 14.dpToPx
        private val YEAR_SIZE = 16.dpToPx

        // Отступ от верхней границы вью до верхней границы графика:
        private val CHART_TOP_MARGIN = 69.dpToPx
        // Отступ от нижней границы вью до нижней границы графика:
        private val CHART_BOTTOM_MARGIN = 46.dpToPx

        // Отступы от верхней границы вью до нижней границы текста:
        private val YEAR_MARGIN = 22.dpToPx
        private val MONTH_MARGIN = 54.dpToPx
        // Отступы от нижней границы вью до нижней границы текста:
        private val VALUE_MARGIN = 25.dpToPx
        private val UNIT_MARGIN = 10.dpToPx

        private val POINT_RADIUS = 3.dpToPx
        private val POINT_STROKE_RADIUS = 1.dpToPx
        private val CURVE_WIDTH = 1.dpToPx
    }

    override fun drawChart(canvas: Canvas, data: List<ChartValue>, sectionWidth: Int) {
        val chartHeight = height.toFloat() - CHART_TOP_MARGIN - CHART_BOTTOM_MARGIN
        val chartBottom = height.toFloat() - CHART_BOTTOM_MARGIN

        var maxValue: Float = 0f
        var minValue: Float = 0f

        data.forEach { item ->
            if (item.value > maxValue) maxValue = item.value
            if (item.value < minValue) minValue = item.value
        }

        // Коэффициент значения (точки на графике) по оси Y
        val valueCoef: Float = chartHeight / (maxValue - minValue)

        // Записываем сюда каждый год, чтобы года не повторялись на графике
        var year: Int = 0

        data.forEachIndexed { index, item ->
            val separatorX = ((index + 1) * sectionWidth).toFloat()
            val pointX = separatorX - sectionWidth / 2
            val pointY = chartBottom - item.value * valueCoef

            if (index == 0) {
                drawCurve(canvas, 0f, pointY, pointX, pointY, chartBottom)
            }

            if (index != data.lastIndex) {
                val nextSeparatorX = ((index + 2) * sectionWidth).toFloat()
                val nextPointX = nextSeparatorX - sectionWidth / 2
                val nextPointY = chartBottom - data[index + 1].value * valueCoef

                drawSeparator(canvas, separatorX, 0f, height.toFloat())
                drawCurve(canvas, pointX, pointY, nextPointX, nextPointY, chartBottom)
            } else {
                drawCurve(canvas, pointX, pointY, width.toFloat(), pointY, chartBottom)
            }

            drawPoint(canvas, pointX, pointY, POINT_RADIUS + POINT_STROKE_RADIUS, Color.WHITE)
            drawPoint(canvas, pointX, pointY, POINT_RADIUS, chartColor)

            if (year != item.year) {
                drawText(canvas, pointX, YEAR_MARGIN, item.year.toString(), passiveColor, YEAR_SIZE)
                year = item.year
            }
            drawText(canvas, pointX, MONTH_MARGIN, item.monthStr, passiveColor, TEXT_SIZE)
            drawText(canvas, pointX, height.toFloat() - VALUE_MARGIN, item.value.toString(), valuesColor, TEXT_SIZE, true)
            drawText(canvas, pointX, height.toFloat() - UNIT_MARGIN, "м3", passiveColor, TEXT_SIZE)
        }
    }
}