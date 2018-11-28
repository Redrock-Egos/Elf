package com.egos.elf.ui.activity

import android.os.Bundle
import android.view.Menu
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
        navigation = NavHostFragment.findNavController(nav_fragment)
        common_toolbar.init("我的收藏")
    }

    override fun onActivate() {
        super.onActivate()
        activateListener?.invoke()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_switch, menu)
        menu?.findItem(R.id.menu_switch)?.setOnMenuItemClickListener {
            if (navigation.currentDestination?.id == R.id.star_music_item_fragment) {
                navigation.navigate(R.id.star_playlist_fragment)
            } else {
                navigation.popBackStack()
            }
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu)
    }
}
