package com.egos.elf.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.egos.elf.R
import com.egos.elf.common.base.BaseNoNeedListenActivity
import com.egos.elf.common.utils.loadMoodPlayList

class WelcomeActivity : BaseNoNeedListenActivity() {
    override val resId: Int
        get() = R.layout.activity_welcome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadMoodPlayList()
        Handler().postDelayed({
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
        }, 3 * 1000)
    }
}
