package com.markoapps.weather.customViews

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Size
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import com.afollestad.materialdialogs.utils.MDUtil.dimenPx
import com.markoapps.weather.R
import com.markoapps.weather.utils.px
import kotlin.math.ceil

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
        sunBitmap = resources.getDrawable(resourceId).toBitmap(
                width = iconSize.width,
                height = iconSize.height)
        updateDrawing(width, height)
        invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private var iconSize: Size = Size(40.px, 40.px)

    private var progress: Float = 0.5f

    private var sunLocation = Point(0, 0)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    private var sunBitmap: Bitmap? = null

    private var path: Path? = null

    private fun updateDrawing(w: Int, h: Int) {
        path = createPath(w, h)
        sunBitmap?.let { sunBitmap ->
            sunLocation = Point(
                    (progress * w - sunBitmap.width / 2).toInt(),
                    (getSunHigh(progress, h) - sunBitmap.height / 2).toInt())
        }
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

            if(sunBitmap != null) {
                drawBitmap(sunBitmap!!, sunLocation.x.toFloat(), sunLocation.y.toFloat(), paint)
            }
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
        val iconHeight = sunBitmap?.height ?: 0
        return (curve(x) * (sizeY - iconHeight).toFloat()) * -1f + sizeY - iconHeight / 2
    }

//    private fun curve(x: Float): Float {
//        return when {
//            x <= 0.25 -> 19 * (x) * (x - 0.25) + 0.3
//            x <= 0.75 -> -11 * (x - 0.25) * (x - 0.75) + 0.3
//            x > 0.25 -> 19 * (x - 0.75) * (x - 1) + 0.3
//            else -> 0f
//        }.toFloat()
//
//    }

    private fun curve(x: Float): Float {
        return (Math.sin(x.toDouble() * Math.PI * 2 - Math.PI / 2) / 2 + 0.5)
        .toFloat()

    }
}