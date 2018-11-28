package com.egos.elf.model

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.egos.elf.App
import com.egos.elf.common.bean.PiazzaData
import com.egos.elf.common.bean.moe.PlayList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/*
 * Created by Cchanges on 2018/11/27
 */
class PiazzaViewModel : ViewModel() {
    private var currentMusicList: PlayList? = null
    private var isInit = false
    private var tempList = mutableListOf<PiazzaData>()

    var commentList: MutableLiveData<MutableList<PiazzaData>> = MutableLiveData()

    @SuppressLint("CheckResult")
    private fun addData() {
        Observable.interval(5, 5, TimeUnit.MINUTES)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                currentMusicList?.let { list ->
                    val pos = (Math.random() * list.tracks.size).toInt()
                    val avatarPos = (Math.random() * 32).toInt()
                    val time = (Math.random() * 60).toInt()
                    tempList.add(
                        0,
                        PiazzaData(
                            list.tracks[pos], "${time}分钟",
                            "http://picture.cynthial.cn/avatar$avatarPos.jpg"
                        )
                    )
                }
                commentList.value = tempList
            }
    }

    fun initData() {
        if (isInit)
            return
        currentMusicList = App.playListManager.getCurrentPlayList()
        val total = (Math.random() * 10).toInt()
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
        addData()
        isInit = true
    }

    fun updateData() {
        isInit = false
        initData()
    }
}