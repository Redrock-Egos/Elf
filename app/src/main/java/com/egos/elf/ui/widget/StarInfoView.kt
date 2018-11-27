package com.egos.elf.ui.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.egos.elf.R
import com.egos.elf.common.bean.Mood
import com.egos.elf.common.utils.gone
import com.egos.elf.common.utils.visible
import kotlinx.android.synthetic.main.view_star_info.view.*
import java.util.*

class StarInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_star_info, this, true)
        tv_cancel.setOnClickListener {
            gone()
        }
    }

    fun show(mood: Mood, end: (String) -> Unit) {
        visible()
        tv_date.text = Calendar.getInstance().let {
            "${it.get(Calendar.YEAR)}.${it.get(Calendar.MONTH)}.${it.get(Calendar.DAY_OF_MONTH)}"
        }
        iv_mood.setImageResource(mood.drawableResId)
        tv_confirm.setOnClickListener {
            val input = input.editText?.text.toString()
            if (input.isBlank()) {
                end("默认收藏")
            } else {
                end(input)
            }
            gone()
        }
        tv_skip.setOnClickListener {
            end("默认收藏")
            gone()
        }
    }
}