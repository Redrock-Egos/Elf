package com.egos.elf.common.bean

import android.support.annotation.DrawableRes
import com.egos.elf.R

enum class Mood(@DrawableRes val drawableResId: Int) {
    HAPPY(R.drawable.ic_mood_happy),
    UNHAPPY(R.drawable.ic_mood_unhappy),
    CLAM(R.drawable.ic_mood_clam),
    EXCITING(R.drawable.ic_mood_exciting);
}