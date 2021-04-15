package com.yulmaso.charttest.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 *  Created by yulmaso
 *  Date: 15.04.21
 */
class CurveView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val path = Path()
    private val paint = Paint().apply { setStyle(Paint.Style.STROKE) }

    private var x: Float? = null
    private var y: Float? = null
    private var x1: Float? = null
    private var y1: Float? = null
    private var x2: Float? = null
    private var y2: Float? = null
    private var x3: Float? = null
    private var y3: Float? = null

    fun setData(x: Float, y: Float, x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float) {
        this.x = x * 2
        this.y = y * 2
        this.x1 = x1 * 2
        this.y1 = y1 * 2
        this.x2 = x2 * 2
        this.y2 = y2 * 2
        this.x3 = x3 * 2
        this.y3 = y3 * 2
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (
            canvas == null ||
            x == null ||
            y == null ||
            x1 == null ||
            y1 == null ||
            x2 == null ||
            y2 == null ||
            x3 == null ||
            y3 == null
        ) return

        path.reset()

        paint.setColor(Color.RED)
        paint.setStrokeWidth(3f)
        path.moveTo(x!!, y!!)
        path.cubicTo(x1!!, y1!!, x2!!,y2!!, x3!!, y3!!)
        canvas.drawPath(path, paint);

        path.reset();
        paint.color = Color.GRAY;
        paint.setStrokeWidth(1f);
        path.moveTo(x!!, y!!);
        path.lineTo(x1!!, y1!!);
        path.lineTo(x2!!, y2!!);
        path.lineTo(x3!!, y3!!);
        canvas.drawPath(path, paint);
    }

}