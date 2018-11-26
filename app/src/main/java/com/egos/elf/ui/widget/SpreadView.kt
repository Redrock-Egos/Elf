package com.egos.elf.ui.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.egos.elf.common.utils.screenWidth

/*
 * Create by Cchanges on 2018/11/7
 */
class SpreadView : View {

    private lateinit var background: Bitmap
    private val discSize: Float
        get() = (230.0 / 375.0 * screenWidth).toFloat()
    private val picSize: Float
        get() = (220.0 / 375.0 * screenWidth).toFloat()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initBackground()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMeasure = View.MeasureSpec.makeMeasureSpec(discSize.toInt(), MeasureSpec.EXACTLY)
        val heightMeasure = View.MeasureSpec.makeMeasureSpec(discSize.toInt(), MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasure, heightMeasure)
    }

    private fun initBackground() {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#C0D2F0")
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        background = Bitmap.createBitmap(discSize.toInt(), discSize.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(background)
        canvas.drawCircle(discSize / 2f, discSize / 2f, picSize / 2f, paint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, 0f, 0f, null)
    }
}