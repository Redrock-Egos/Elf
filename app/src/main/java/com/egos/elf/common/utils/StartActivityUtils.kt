package com.egos.elf.common.utils

import android.content.Context
import android.content.Intent
import com.egos.elf.ui.activity.MusicDetailActivity
import com.egos.elf.ui.activity.ShowStarActivity

fun startShowStarActivity(context: Context) {
    context.startActivity(Intent(context, ShowStarActivity::class.java))
}

fun startMusicDetailActivity(context: Context) {
    context.startActivity(Intent(context, MusicDetailActivity::class.java))
}