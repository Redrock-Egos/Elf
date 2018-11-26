package com.egos.elf.common.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.egos.elf.App.Companion.context


val screenWidth by lazy {
    DisplayMetrics().apply {
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(this)
    }.widthPixels
}
val screenHeight by lazy {
    DisplayMetrics().apply {
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(this)
    }.heightPixels
}

private val density by lazy { context.resources.displayMetrics.density }
private val scaleDensity by lazy { context.resources.displayMetrics.scaledDensity }

fun dp(dp: Float) = dp * density + 0.5f
fun sp(sp: Float) = sp * scaleDensity + 0.5f