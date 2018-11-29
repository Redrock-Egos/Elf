package com.egos.elf.ui.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.egos.elf.R
import com.egos.elf.common.utils.screenWidth

/*
 * Created by Cchanges on 2018/11/26
 */
class DiskView @JvmOverloads constructor(
    context: Context, attr: AttributeSet? = null, defStyleInt: Int = 0
) : AppCompatImageView(context, attr, defStyleInt) {

    private var backgroundColor = "#C0D2F0"
    private val diskPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor(backgroundColor)
        strokeWidth = (discSize - picSize) / 4f
    }
    private var disk: Bitmap
    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "alpha", 1f, 0f, 1f)
            .apply {
                duration = 800
                interpolator = LinearInterpolator()
                repeatCount = 0
            }
    }


    private val picSize: Float
        get() = (220.0 / 375.0 * screenWidth).toFloat()

    private val discSize: Float
        get() = (230.0 / 375.0 * screenWidth).toFloat()

    init {
        disk = initDisk(null)
    }

    private fun initDisk(draw: Drawable?): Bitmap {
        val rawBitmap = if (draw == null) {
            (ContextCompat.getDrawable(context, R.drawable.ic_default_bottom_music_icon)
                    as BitmapDrawable).bitmap
        } else {
            (draw as BitmapDrawable).bitmap
        }
        val mShader = BitmapShader(
            rawBitmap,
            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        )
        val mMatrix = Matrix()
        mMatrix.setScale(
            picSize / rawBitmap.width,
            picSize / rawBitmap.height
        )
        mShader.setLocalMatrix(mMatrix)
        diskPaint.shader = mShader
        val radius = picSize / 2
        val temp = Bitmap.createBitmap(discSize.toInt(), discSize.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(temp)
        canvas.drawCircle(discSize / 2, discSize / 2, radius, diskPaint)
        canvas.drawCircle(discSize / 2, discSize / 2, radius, mBackgroundPaint)
        return temp
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMeasure = View.MeasureSpec.makeMeasureSpec(discSize.toInt(), MeasureSpec.EXACTLY)
        val heightMeasure = View.MeasureSpec.makeMeasureSpec(discSize.toInt(), MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasure, heightMeasure)
    }

    override fun onDraw(canvas: Canvas) {
        val left = if (width - discSize <= 0) 0 else ((width - discSize) / 2).toInt()
        val top = if (height - discSize <= 0) 0 else ((height - discSize) / 2).toInt()
        canvas.drawBitmap(disk, left.toFloat(), top.toFloat(), null)
    }

    fun upDatePicture(drawable: Drawable) {
        var isUpdate = false
        animator.addUpdateListener { it ->
            if ((it.animatedValue as Float) < 0.2 && !isUpdate) {
                disk = initDisk(drawable)
                postInvalidate()
                isUpdate = true
            }
        }
        animator.start()
    }

    fun changeColor(color : String) {
        backgroundColor = color
    }
}