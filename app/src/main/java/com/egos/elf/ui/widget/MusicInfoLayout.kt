package com.egos.elf.ui.widget

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import com.egos.elf.R
import com.egos.elf.common.bean.moe.Music
import com.egos.elf.common.utils.screenWidth
import kotlinx.android.synthetic.main.include_disk.view.*

/*
 * Created by Cchanges on 2018/11/26
 */
class MusicInfoLayout @JvmOverloads constructor(
   context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val STATE_PLAYING = 1
        const val STATE_PAUSE = 2
        const val STATE_STOP = 3
    }

    var currentState = STATE_STOP
    private lateinit var totalAnimator: ObjectAnimator
    private lateinit var backgroundAnimator: ObjectAnimator

    private val discSize: Float
        get() = (225.0 / 375.0 * screenWidth).toFloat()

    init {
        LayoutInflater.from(context).inflate(R.layout.include_disk, this, true)
        initAnimator()
    }

    private fun initAnimator() {
        totalAnimator = ObjectAnimator.ofFloat(
            disk_view, "rotation", 0f, 360f
        ).apply {
            duration = 10000
            interpolator = LinearInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
        }
        val alphaValues = PropertyValuesHolder.ofFloat("alpha", 0f)
        val scaleXValues = PropertyValuesHolder.ofFloat("scaleX", screenWidth / discSize * 0.8f)
        val scaleYValues = PropertyValuesHolder.ofFloat("scaleY", screenWidth / discSize * 0.8f)
        backgroundAnimator = ObjectAnimator.ofPropertyValuesHolder(
            spread_view, alphaValues, scaleXValues, scaleYValues
        ).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
        }
    }

    fun initMusicTextData(currentMusic: Music) {
        tv_name_song.text = currentMusic.name
        tv_name_singer.text = currentMusic.artistNameStr
    }

    fun changeDiskState() {
        when (currentState) {
            STATE_STOP -> {
                totalAnimator.start()
                backgroundAnimator.start()
                currentState = STATE_PLAYING
            }
            STATE_PAUSE -> {
                totalAnimator.pause()
                backgroundAnimator.pause()
                currentState = STATE_PLAYING
            }
            STATE_PLAYING -> {
                totalAnimator.resume()
                backgroundAnimator.resume()
                currentState = STATE_PAUSE
            }
        }
    }

    fun stopDisk() {
        totalAnimator.end()
        backgroundAnimator.end()
    }
}

