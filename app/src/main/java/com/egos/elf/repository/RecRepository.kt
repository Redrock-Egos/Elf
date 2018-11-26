package com.egos.elf.repository

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.egos.elf.common.bean.RecAlbum
import com.egos.elf.common.bean.moe.Album
import com.egos.elf.common.net.ApiGenerator
import com.egos.elf.common.utils.ToastUtils
import com.egos.elf.common.utils.safeSubscribeBy
import com.egos.elf.common.utils.setSchedulers

/**
 * create by:Fxymine4ever
 * time: 2018/11/25
 */
class RecRepository {
    companion object {
        val instance: RecRepository by lazy { RecRepository() }
    }

    private var data :MutableLiveData<RecAlbum> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getRecAlbum(mood:String):MutableLiveData<RecAlbum>{
        ApiGenerator
                .getElfFApiService()
                .getRecAlbum(mood)
                .setSchedulers()
                .subscribe {
                    it->
                    data.value = it
                }

        return data
    }
}