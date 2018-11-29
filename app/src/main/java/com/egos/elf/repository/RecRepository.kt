package com.egos.elf.repository

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.egos.elf.common.bean.moe.PlayListWrapper
import com.egos.elf.common.net.ApiGenerator
import com.egos.elf.common.utils.setSchedulers

/**
 * create by:Fxymine4ever
 * time: 2018/11/25
 */
class RecRepository {
    companion object {
        val instance: RecRepository by lazy { RecRepository() }
    }

    private var data :MutableLiveData<PlayListWrapper> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getRecAlbum(mood:String):MutableLiveData<PlayListWrapper>{
        ApiGenerator
                .getElfFApiService()
                .getRecAlbum(mood)
                .setSchedulers()
                .subscribe {
                    it->
                    it.result.description = "摩西默默扫视着正午烈日炙烤下的荒漠，隐藏在牛仔帽檐下的双眼透出无比坚定的意味。他骑着一匹高大但消瘦的老马，一手搭在腰畔枪套里的柯尔特M1873上，那坚硬踏实的触感令他安心不少。他的身后，茫茫风沙里，许多成年男子荷枪骑马，拱卫着一辆辆大篷车，长长的队伍迤逦前行……"
                    it.result.name = "绝佳配乐 | 黑暗美学中的荒诞主义"
                    it.result.coverImgUrl = "https://s1.ax1x.com/2018/11/29/FeuBCQ.jpg"
                    data.value = it
                }

        return data
    }
}