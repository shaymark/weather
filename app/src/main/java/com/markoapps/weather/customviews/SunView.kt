package com.markoapps.weather.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View

class SunView(context: Context?) : View(context) {

    private var shapePath: Path? = null
    private var strokePaint: Paint? = null
    private var fillPaint: Paint? = null

    fun StrokeFill(context: Context?) {
//        shapePath = Path()
//        shapePath = path
        fillPaint = Paint()
        fillPaint.setColor(Color.WHITE)
        fillPaint.setStyle(Paint.Style.FILL)
        fillPaint.setStrokeWidth(0)
        strokePaint = Paint()
        strokePaint.setColor(Color.RED)
        strokePaint.setStyle(Paint.Style.STROKE)
        strokePaint.setStrokeWidth(3)
        // TODO Auto-generated constructor stub
    }

    override fun onDraw(canvas: Canvas) {
        // TODO Auto-generated method stub
        //canvas.drawColor(Color.BLACK);
        super.onDraw(canvas)
//        canvas.drawPath(shapePath, fillPaint)
//        canvas.drawPath(shapePath, strokePaint)
    }
}