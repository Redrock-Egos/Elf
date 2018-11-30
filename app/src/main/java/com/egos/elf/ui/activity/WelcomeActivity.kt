package com.egos.elf.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.egos.elf.R
import com.egos.elf.common.base.BaseNoNeedListenActivity
import com.egos.elf.common.utils.loadMoodPlayList
import com.egos.elf.common.utils.screenWidth
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseNoNeedListenActivity() {
    override val resId: Int
        get() = R.layout.activity_welcome

    override val needSlide: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackground()
        loadMoodPlayList()
        Handler().postDelayed({
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
            finish()
        }, 3 * 1000)
    }

    private fun setBackground() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_welcome) ?: return
        val scaledHeight = (screenWidth * 1f / bitmap.width) * bitmap.height
        val newBitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, scaledHeight.toInt(), false)
        iv_bg_welcome.viewTreeObserver.addOnGlobalLayoutListener {
            val viewHeight = iv_bg_welcome.measuredHeight
            val temp = newBitmap.height - viewHeight
            if (temp <= 0) {
                iv_bg_welcome.apply {
                    setImageDrawable(BitmapDrawable(resources,bitmap))
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            } else {
                val final = Bitmap.createBitmap(newBitmap, 0, temp, screenWidth, newBitmap.height - temp)
                iv_bg_welcome.setImageDrawable(BitmapDrawable(resources, final))
            }
        }
    }
}
