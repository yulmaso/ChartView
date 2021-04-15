package com.yulmaso.charttest.chartView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.yulmaso.charttest.R
import java.util.*

/**
 *  Created by yulmaso
 *  Date: 15.04.21
 */
abstract class ChartView<T : ChartValue>(context: Context, attrs: AttributeSet) :
    View(context, attrs)
{
    private val paint = Paint()
    private val path = Path()

    // Attrs
    protected val chartColor: Int
    protected val valuesColor: Int
    protected val passiveColor: Int
    protected val separatorColor: Int
    protected val fillColor: Int
    protected val sectionCount: Int

    private var viewportWidth: Int? = null

    private var data = ArrayList<T>()

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

    /**
     *  Логика отрисовки графика. Вызывается в onDraw.
     *
     *  @param canvas       - Canvas из onDraw
     *  @param data         - Список значений
     *  @param sectionWidth - Ширина одной секции графика в пикселах
     */
    abstract fun drawChart(canvas: Canvas, data: List<T>, sectionWidth: Int)

    /**
     *  Здесь задаём ширину вью. Вызывается перед onDraw.
     */
    final override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMeasure = viewportWidth?.let { data.size * it / sectionCount } ?: widthMeasureSpec
        setMeasuredDimension(widthMeasure, heightMeasureSpec)
    }

    /**
     *  Собственно метод отрисовки. Вызывается после invalidate().
     */
    final override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (data.isEmpty() || canvas == null) return
        viewportWidth?.let { drawChart(canvas, data, it / sectionCount) }
    }

    /**
     *  Метод установки данных во вью.
     */
    open fun setData(data: List<T>, viewportWidth: Int) {
        this.data.clear()
        this.data.addAll(data.sorted())
        this.viewportWidth = viewportWidth
        invalidate()
    }

    /**
     *  Метод добавления данных во вью. (Вызывает полную перерисовку графика)
     */
    open fun addData(data: List<T>, viewportWidth: Int) {
        val temp = ArrayList<T>()
        temp.addAll(data)
        temp.addAll(this.data)

        this.data.clear()
        this.data.addAll(temp.sorted())
        this.viewportWidth = viewportWidth
        invalidate()
    }

    /**
     *  Метод отрисовки точки на графике.
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
     *  Метод отрисовки разделителя секций.
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
     *  Метод отрисовки кривой между двумя точками на графике.
     *
     *  @param bottom   - set this as yBottom of chart to fill a path under curve
     */
    protected fun drawCurve(canvas: Canvas, x1: Float, y1: Float, x2: Float, y2: Float, yBottom: Float? = null) {
        paint.reset()
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = 2f
        paint.color = chartColor

        // Рассчитываем середину отрезка по оси X
        val xMiddle = (x1 + x2) / 2

        // Рисуем кривую Безье
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
     *  Метод отрисовки текста.
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
}