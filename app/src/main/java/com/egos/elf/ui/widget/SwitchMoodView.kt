package com.egos.elf.ui.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import com.egos.elf.App
import com.egos.elf.R
import com.egos.elf.common.bean.Mood.*
import com.egos.elf.common.utils.PlayListManager.Companion.DEFAULT_PLAY_LIST_KEY
import com.egos.elf.common.utils.invisible
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
    private var isDefault = false
    private var isWhite = false
    private val background by lazy { BitmapFactory.decodeResource(resources,R.drawable.bg_frame_short) }
    private var listener: NotifyDataChangedListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.include_buttons, this, true)
        updateRes()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.makeMeasureSpec(background.width, MeasureSpec.EXACTLY)
        val height = MeasureSpec.makeMeasureSpec(background.height, MeasureSpec.EXACTLY)
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

    private fun updateData(pos: Int) {
        App.playListManager.apply {
            val temp = getKeySequence(pos)
            updateKeySequence(temp)
            if (listener != null) {
                listener!!.currentState()
            }
        }
        if (isDefault) {
            isDefault = false
        }
        changeMood()
        updateRes()
    }

    fun updateRes() {
        currentRes.clear()
        App.playListManager.useKeySequence {
            if (it == DEFAULT_PLAY_LIST_KEY) {
                if (!isWhite){
                    currentRes.add(R.drawable.ic_mood_default)
                } else {
                    currentRes.add(R.drawable.ic_mood_default_white)
                }
                return@useKeySequence
            }
            currentRes.add(
                if (!isWhite){
                    when (valueOf(it)) {
                        HAPPY -> R.drawable.ic_mood_happy
                        CLAM -> R.drawable.ic_mood_clam
                        UNHAPPY -> R.drawable.ic_mood_unhappy
                        EXCITING -> R.drawable.ic_mood_exciting
                    }
                } else {
                    when (valueOf(it)) {
                        HAPPY -> R.drawable.ic_mood_happy_white
                        CLAM -> R.drawable.ic_mood_clam_white
                        UNHAPPY -> R.drawable.ic_mood_unhappy_white
                        EXCITING -> R.drawable.ic_mood_exciting_white
                    }
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

    fun setListener(listener: NotifyDataChangedListener) {
        this.listener = listener
    }

    fun changeStyle(){
        isWhite = if (!isWhite){
            iv_bg.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.bg_frame_white))
            true
        } else {
            iv_bg.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.bg_frame_short))
            false
        }
        updateRes()
    }

    fun setDefaultMood() {
        App.playListManager.updateKeySequence(DEFAULT_PLAY_LIST_KEY)
        updateRes()
        isDefault = true
    }

    interface NotifyDataChangedListener {
        fun currentState()
    }

}
