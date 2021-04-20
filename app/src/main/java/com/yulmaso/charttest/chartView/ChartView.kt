package com.yulmaso.charttest.chartView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import com.yulmaso.charttest.R
import com.yulmaso.charttest.chartView.ChartView.Chart
import java.util.*

/**
 *  Created by yulmaso
 *  Date: 20.04.21
 *
 *  Abstract view class, that contains methods to ease chart drawing.
 *  This is HorizontalScrollView container that holds actual [Chart] view.
 */
abstract class ChartView<T : ChartValue>(context: Context, attrs: AttributeSet) :
    HorizontalScrollView(context, attrs)
{
    // Attrs
    protected val chartColor: Int
    protected val valuesColor: Int
    protected val passiveColor: Int
    protected val separatorColor: Int
    protected val fillColor: Int
    protected val sectionCount: Int

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ChartView, 0, 0).apply {
            chartColor = getColor(R.styleable.ChartView_chartColor, Color.BLACK)
            valuesColor = getColor(R.styleable.ChartView_valuesColor, Color.BLACK)
            passiveColor = getColor(R.styleable.ChartView_passiveColor, Color.BLACK)
            separatorColor = getColor(R.styleable.ChartView_separatorColor, Color.BLACK)
            fillColor = getColor(R.styleable.ChartView_fillColor, Color.BLACK)
            sectionCount = getInt(R.styleable.ChartView_sectionCount, 6)
        }
    }

    private val paint = Paint()
    private val path = Path()

    private var data = ArrayList<T>()

    private var chart: Chart? = null

    /**
     *  Chart draw logic callback. Is called from [onDraw] of inner [chart].
     *
     *  @param canvas       - Canvas из onDraw
     *  @param data         - Список значений
     *  @param sectionWidth - Ширина одной секции графика в пикселах
     */
    abstract fun drawChart(canvas: Canvas, data: List<T>, sectionWidth: Int)

    /**
     *  Override this method if you want to change the data before drawing.
     *
     *  @param data - data from [setData] or concatenated list from [addData]
     */
    open fun modifyData(data: List<T>): List<T> {
        return data.sorted()
    }

    /**
     *  Set [data] to view.
     *
     *  @param data             - chart data
     */
    fun setData(data: List<T>) {
        this.data.clear()
        this.data.addAll(modifyData(data))
        initChart()
    }

    /**
     *  Add [data] to view. (Leads to full redraw).
     *
     *  @param data             - chart data
     */
    fun addData(data: List<T>) {
        val temp = ArrayList<T>()
        temp.addAll(data)
        temp.addAll(this.data)

        this.data.clear()
        this.data.addAll(modifyData(temp))
        initChart()
    }

    /**
     *  Draw a circle point on [canvas] with center at ([x], [y]) and custom [radius] & [color].
     */
    protected fun drawPoint(canvas: Canvas, x: Float, y: Float, radius: Float, color: Int) {
        paint.reset()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = 2f
        paint.color = color

        canvas.drawCircle(x, y, radius, paint)
    }

    /**
     *  Draw a vertical separator on [canvas] which starts at ([x], [top]) and ends at ([x], [bottom]]).
     */
    protected fun drawSeparator(canvas: Canvas, x: Float, top: Float, bottom: Float) {
        paint.reset()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = 2f
        paint.color = separatorColor

        canvas.drawLine(x, top, x, bottom, paint)
    }

    /**
     *  Draw a vertical separator on [canvas] which starts at ([x], [top]) and ends at ([x], [bottom]])
     *  which has gradient color.
     */
    protected fun drawGradientSeparator(canvas: Canvas, x: Float, top: Float, bottom: Float) {
        val gradient = LinearGradient(
            x, bottom, x, top,
            intArrayOf(Color.WHITE, separatorColor, Color.WHITE),
            null,
            Shader.TileMode.MIRROR
        )
        paint.reset()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = 2f
        paint.isDither = true
        paint.shader = gradient

        canvas.drawLine(x, top, x, bottom, paint)
    }

    /**
     *  Draw bezier curve on [canvas] which starts at ([x1], [y1]) and ends at ([x2], [y2]).
     *
     *  @param yBottom - set this as yBottom of chart to fill a path under curve with [fillColor]
     */
    protected fun drawCurve(canvas: Canvas, x1: Float, y1: Float, x2: Float, y2: Float, color: Int, yBottom: Float? = null) {
        paint.reset()
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = 2f
        paint.color = color

        val xMiddle = (x1 + x2) / 2

        path.reset()
        path.moveTo(x1, y1)
        path.cubicTo(xMiddle, y1, xMiddle, y2, x2, y2)
        canvas.drawPath(path, paint)
        yBottom?.let {
            path.lineTo(x2, yBottom)
            path.lineTo(x1, yBottom)
            path.close()
            path.fillType = Path.FillType.EVEN_ODD

            paint.reset()
            paint.style = Paint.Style.FILL
            paint.color = fillColor
            canvas.drawPath(path, paint)
        }
    }

    /**
     *  Draw the [text] with [color] on [canvas] which bottom center is at ([x], [y]).
     *  Set [bold] to true to make this text bold.
     */
    protected fun drawText(canvas: Canvas, x: Float, y: Float, text: String, color: Int, textSize: Float, bold: Boolean = false) {
        paint.reset()
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.color = color
        paint.textSize = textSize
        paint.textAlign = Paint.Align.CENTER
        paint.isFakeBoldText = bold
        canvas.drawText(text, x, y, paint)
    }

    private fun initChart() {
        chart?.let { removeView(it) }
        chart = Chart(context)
        addView(chart)
    }

    /**
     *  Private view that holds chart.
     */
    private inner class Chart(context: Context) : View(context) {

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            val widthMeasure = data.size * widthMeasureSpec / sectionCount
            setMeasuredDimension(widthMeasure, heightMeasureSpec)
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            if (data.isEmpty() || canvas == null) return
            drawChart(canvas, data, measuredWidth / data.size)
        }
    }
}