package com.egos.elf.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.egos.elf.App
import com.egos.elf.common.bean.PiazzaData
import com.egos.elf.common.bean.moe.PlayList

/*
 * Created by Cchanges on 2018/11/27
 */
class PiazzaViewModel : ViewModel() {
    private var currentMusicList: PlayList? = null
    private var tempList = mutableListOf<PiazzaData>()

    var commentList: MutableLiveData<MutableList<PiazzaData>> = MutableLiveData()

    fun initData() {
        if (tempList.isNotEmpty())
            tempList.clear()
        currentMusicList = App.playListManager.getCurrentPlayList()
        val total = (Math.random() * 10 + 1).toInt()
        currentMusicList?.let { list ->
            repeat(total) {
                val pos = (Math.random() * list.tracks.size).toInt()
                val avatarPos = (Math.random() * 32).toInt()
                val time = (Math.random() * 60).toInt()
                tempList.add(
                    PiazzaData(
                        list.tracks[pos], "${time}分钟",
                        "http://picture.cynthial.cn/avatar$avatarPos.jpg"
                    )
                )
            }
        }
        commentList.value = tempList
    }
}