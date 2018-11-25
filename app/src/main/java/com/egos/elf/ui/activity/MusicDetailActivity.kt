package com.egos.elf.ui.activity

import android.os.Bundle
import com.egos.elf.R
import com.egos.elf.common.base.BaseActivity

class MusicDetailActivity : BaseActivity() {
    override fun onActivate() {
        // todo listener
    }

    override fun onPlayStart() {
        // todo listener
    }

    override fun onPlayPause() {
        // todo listener
    }

    override fun onPlayStop() {
        // todo listener
    }

    override fun onPlayProgressUpdate(progress: Int) {
        // todo listener
    }

    override val resId: Int
        get() = R.layout.activity_music_detail


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        common_toolbar.init("")
    }

}
