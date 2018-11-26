package com.egos.elf.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.egos.elf.R
import com.egos.elf.common.base.BaseNoNeedListenActivity
import com.egos.elf.common.bean.moe.PlayListWrapper
import com.egos.elf.common.net.ApiGenerator
import com.egos.elf.common.utils.withSPCache
import com.egos.elf.ui.adapter.RecDetailAdapter
import kotlinx.android.synthetic.main.activity_rec_detail.*

class RecDetailActivity : BaseNoNeedListenActivity() {
    private val moeService = ApiGenerator.getMoeApiService()
    private lateinit var mAdapter: RecDetailAdapter


    override val resId: Int
        get() = R.layout.activity_rec_detail

    private var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.init("")
        getBundleData()
    }

    private fun getBundleData() {
        intent.extras?.let { it ->
            id = it.getLong("albumId")
            Log.d("fxy", id.toString())
            withSPCache(
                    id.toString(),
                    PlayListWrapper::class.java,
                    { moeService.getPlayList(id) },
                    { wrapper ->
                        rec_detail_tv_title.text = wrapper.result.name
                        initView(wrapper)
                        mAdapter.setWrapper(wrapper)
                    })
        }
    }

    private fun initView(data: PlayListWrapper) {
        mAdapter = RecDetailAdapter(this, data.result.tracks, intArrayOf(R.layout.item_rv_rec))
        rec_detail_rv.adapter = mAdapter
        rec_detail_rv.layoutManager = LinearLayoutManager(this)
    }
}
