package com.markoapps.weather.customViews

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.markoapps.weather.R
import kotlin.math.ceil

class SunsetSunriseView@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    fun setProgress(progress: Float) {
        this.progress = progress
        updateDrawing(width, height)
        invalidate()
    }

    fun setSunBitmap(resourceId: Int) {
        sunBitmap = resources.getDrawable(resourceId).toBitmap()
        updateDrawing(width, height)
        invalidate()
    }

    private var progress: Float = 0.5f

    private var sunLocation = Point(0, 0)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    private var sunBitmap: Bitmap = resources.getDrawable(R.drawable.ic_sun).toBitmap()

    private var path: Path? = null

    private fun updateDrawing(w: Int, h: Int) {
        path = createPath(w, h)
        sunLocation = Point(
                (progress * w - sunBitmap.width / 2).toInt(),
                (getSunHigh(progress, h) - sunBitmap.height / 2).toInt()
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        updateDrawing(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            if(path != null) {
                drawPath(path!! , paint)
            }

            drawBitmap(sunBitmap, sunLocation.x.toFloat(), sunLocation.y.toFloat(), paint)
        }

    }

    private fun createPath(sizeX: Int, sizeY: Int): Path {
        return Path().apply {
            val dx = 0.005f
            val steps = ceil(1 / dx).toInt() + 1
            repeat(steps) { stepCount ->
                val x = dx * stepCount
                lineTo(x * sizeX , getSunHigh(x, sizeY))
            }
        }
    }

    private fun getSunHigh(x: Float, sizeY: Int) : Float{
        return (curve(x) * (sizeY - sunBitmap.height).toFloat()) * -1f + sizeY - sunBitmap.height / 2
    }

    private fun curve(x: Float): Float {
        return when {
            x <= 0.25 -> 19 * (x) * (x - 0.25) + 0.3
            x <= 0.75 -> -11 * (x - 0.25) * (x - 0.75) + 0.3
            x > 0.25 -> 19 * (x - 0.75) * (x - 1) + 0.3
            else -> 0f
        }.toFloat()

    }
}