package com.egos.elf.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.egos.elf.App
import com.egos.elf.R
import com.egos.elf.common.base.BaseNoNeedListenActivity
import com.egos.elf.common.utils.PlayListManager
import com.egos.elf.common.utils.ToastUtils
import com.egos.elf.common.utils.putBeanToSP
import com.egos.elf.common.utils.startMusicDetailActivity
import com.egos.elf.model.RecModel
import com.egos.elf.ui.adapter.RecDetailAdapter
import kotlinx.android.synthetic.main.activity_rec_detail.*

class RecDetailActivity : BaseNoNeedListenActivity() {
    private lateinit var mAdapter: RecDetailAdapter
    private lateinit var model: RecModel

    override val resId: Int
        get() = R.layout.activity_rec_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.init("")
        model = ViewModelProviders.of(this).get(RecModel::class.java)
        initView()
        loadData()
    }

    private fun initView() {
        mAdapter = RecDetailAdapter(this, mutableListOf(), intArrayOf(R.layout.item_rv_rec))
        rec_detail_rv.adapter = mAdapter
        rec_detail_rv.layoutManager = LinearLayoutManager(this)

    }

    private fun loadData() {
        val data = App.playListManager.getTempPlayList("temp")
        data?.let {
            rec_detail_tv_title.text = data.name
            putBeanToSP(data.id.toString(), data)
            mAdapter.refreshData(data.tracks)
            mAdapter.listenr = {position->
                App.playListManager.apply {
                    updatePlayList(PlayListManager.DEFAULT_PLAY_LIST_KEY,data)
                    updateKeySequence(PlayListManager.DEFAULT_PLAY_LIST_KEY, false)
                }
                musicControlBinder?.setPlayPos(position)
                startMusicDetailActivity(this@RecDetailActivity)
            }
        }
    }
}
