package com.egos.elf.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.egos.elf.App
import com.egos.elf.R
import com.egos.elf.common.base.BaseNoNeedListenActivity
import com.egos.elf.common.bean.moe.PlayList
import com.egos.elf.common.utils.PlayListManager
import com.egos.elf.common.utils.startMusicDetailActivity
import kotlinx.android.synthetic.main.activity_show_star.*

class ShowStarActivity : BaseNoNeedListenActivity() {
    override val resId: Int
        get() = R.layout.activity_show_star

    private lateinit var navigation: NavController

    var activateListener: (() -> Unit)? = null

    fun onMusicSelect(playList: PlayList, pos: Int) {
        App.playListManager.updatePlayList(PlayListManager.DEFAULT_PLAY_LIST_KEY, playList)
        App.playListManager.updateKeySequence(PlayListManager.DEFAULT_PLAY_LIST_KEY)
        musicControlBinder?.setPlayPos(pos)
        startMusicDetailActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        common_toolbar.init("我的收藏")

        navigation = NavHostFragment.findNavController(nav_fragment)

    }

    override fun onActivate() {
        super.onActivate()
        activateListener?.invoke()
    }
}
