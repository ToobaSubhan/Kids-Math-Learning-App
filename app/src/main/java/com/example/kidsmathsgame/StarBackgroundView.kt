package com.example.kidsmathsgame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class StarBackgroundView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val stars = mutableListOf<Star>()
    private val paint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        stars.clear()
        repeat(50) {
            stars.add(Star(
                Random.nextFloat() * w,
                Random.nextFloat() * h,
                Random.nextFloat() * 3 + 1,
                Random.nextFloat() * 0.05f + 0.01f
            ))
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        stars.forEach { star ->
            paint.alpha = (star.alpha * 255).toInt()
            canvas.drawCircle(star.x, star.y, star.size, paint)
            star.update()
        }
        invalidate()
    }

    private class Star(var x: Float, var y: Float, val size: Float, var speed: Float) {
        var alpha = Random.nextFloat()
        private var increasing = true

        fun update() {
            if (increasing) {
                alpha += speed
                if (alpha >= 1f) increasing = false
            } else {
                alpha -= speed
                if (alpha <= 0.2f) increasing = true
            }
        }
    }
}
