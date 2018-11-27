package com.egos.elf.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.egos.elf.R
import com.egos.elf.common.base.BaseActivity
import com.egos.elf.common.bean.TestData
import com.egos.elf.ui.adapter.PiazzaAdapter
import kotlinx.android.synthetic.main.activity_piazza.*

class PiazzaActivity : BaseActivity() {
    override fun onPlayStart() = Unit

    override fun onPlayPause() = Unit

    override fun onPlayStop() = Unit

    override fun onPlayProgressUpdate(progress: Int) = Unit

    private lateinit var list : MutableList<TestData>

    override fun onActivate() {
        addTestData()
        rv_piazza.apply {
            adapter = PiazzaAdapter(list)
            layoutManager = LinearLayoutManager(this@PiazzaActivity)
        }
    }

    override val resId: Int
        get() = R.layout.activity_piazza

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.init("一些想说的")
    }

    private fun addTestData() {
        list = mutableListOf()
        musicControlBinder?.getCurrentTrack()?.let {
            list.add(TestData(it,"二十分钟","http://picture.cynthial.cn/image1519927643040"))
        }
        musicControlBinder?.getCurrentTrack()?.let {
            list.add(TestData(it,"一小时","http://picture.cynthial.cn/image1519889754393"))
        }
    }
}
