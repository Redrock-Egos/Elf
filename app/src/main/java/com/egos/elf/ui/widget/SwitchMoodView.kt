package com.egos.elf.ui.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import com.egos.elf.App
import com.egos.elf.R
import com.egos.elf.common.bean.Mood.*
import com.egos.elf.common.utils.invisible
import com.egos.elf.common.utils.screenHeight
import com.egos.elf.common.utils.screenWidth
import com.egos.elf.common.utils.visible
import kotlinx.android.synthetic.main.include_buttons.view.*

/*
 * Created by Cchanges on 2018/11/26
 */
class SwitchMoodView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val views by lazy { mutableListOf(iv_current, iv_bottom, iv_center, iv_top) }
    private val currentRes = mutableListOf<Int>()
    private var isExpand = false

    init {
        LayoutInflater.from(context).inflate(R.layout.include_buttons, this, true)
        App.playListManager.useKeySequence {
            currentRes.add(
                when (valueOf(it)) {
                    HAPPY -> R.drawable.ic_mood_happy
                    CLAM -> R.drawable.ic_mood_clam
                    UNHAPPY -> R.drawable.ic_mood_unhappy
                    EXCITING -> R.drawable.ic_mood_exciting
                }
            )
        }
        initListener()
        updateRes()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.makeMeasureSpec((55 / 375.0 * screenWidth).toInt(), MeasureSpec.EXACTLY)
        val height = MeasureSpec.makeMeasureSpec((225 / 667.0 * screenHeight).toInt(), MeasureSpec.EXACTLY)
        super.onMeasure(width, height)
    }

    private fun changeMood() {
        isExpand = if (isExpand) {
            iv_center.invisible()
            iv_bottom.invisible()
            iv_top.invisible()
            iv_bg.invisible()
            false
        } else {
            iv_center.visible()
            iv_bottom.visible()
            iv_top.visible()
            iv_bg.visible()
            true
        }
    }

    private fun initListener() {
        repeat(views.size) { index ->
            if (index == 0) {
                views[index].setOnClickListener { changeMood() }
            } else {
                views[index].setOnClickListener { updateData(index) }
            }
        }
    }


    private fun updateData(pos : Int) {
        App.playListManager.apply {
            val temp = getKeySequence(pos)
            updateKeySequence(temp)
        }
        updateRes()
    }

    fun updateRes() {
        currentRes.clear()
        App.playListManager.useKeySequence {
            currentRes.add(
                when (valueOf(it)) {
                    HAPPY -> R.drawable.ic_mood_happy
                    CLAM -> R.drawable.ic_mood_clam
                    UNHAPPY -> R.drawable.ic_mood_unhappy
                    EXCITING -> R.drawable.ic_mood_exciting
                }
            )
        }
        repeat(views.size) { index ->
            views[index].setImageDrawable(ContextCompat.getDrawable(context, currentRes[index]))
            if (index == 0) {
                views[index].setOnClickListener { changeMood() }
            } else {
                views[index].setOnClickListener { updateData(index) }
            }
        }
    }

}
