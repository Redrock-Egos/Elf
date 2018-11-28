package com.egos.elf.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.egos.elf.App
import com.egos.elf.R
import com.egos.elf.common.base.BaseNoNeedListenActivity
import com.egos.elf.common.utils.PlayListManager
import com.egos.elf.common.utils.startMusicDetailActivity
import com.egos.elf.model.PiazzaViewModel
import com.egos.elf.ui.adapter.PiazzaAdapter
import com.egos.elf.ui.widget.SwitchMoodView
import kotlinx.android.synthetic.main.activity_piazza.*

class PiazzaActivity : BaseNoNeedListenActivity(),SwitchMoodView.NotifyDataChangedListener {

    private val piazzaModel by lazy { ViewModelProviders.of(this@PiazzaActivity).get(PiazzaViewModel::class.java) }
    private lateinit var adapter : PiazzaAdapter

    override val resId: Int
        get() = R.layout.activity_piazza

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.init("一些想说的")
        adapter = PiazzaAdapter(mutableListOf()){
            smv_piazza.setDefaultMood()
            App.playListManager.updatePlayList(PlayListManager.DEFAULT_PLAY_LIST_KEY, it)
        }
        initData()
        smv_piazza.setListener(this)
        ibtn_more_piazza.setOnClickListener {
            startMusicDetailActivity(this)
        }
    }

    private fun initData(){
        piazzaModel.initData()
        piazzaModel.commentList.observeForever {
            if (it == null)
                return@observeForever
            adapter.setData(it)
        }
        rv_piazza.apply {
            adapter = this@PiazzaActivity.adapter
            layoutManager = LinearLayoutManager(this@PiazzaActivity)
        }
    }

    override fun currentState() {
        piazzaModel.updateData()
    }
}
