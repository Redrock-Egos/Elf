package com.egos.elf.common.utils

import com.egos.elf.App
import com.egos.elf.common.bean.Mood
import com.egos.elf.common.bean.moe.PlayListWrapper
import com.egos.elf.common.net.ApiGenerator
import io.reactivex.schedulers.Schedulers

private const val PLAY_LIST_ID_SP_NAME = "PLAY_LIST_ID"
fun loadMoodPlayList() {
    val moeService = ApiGenerator.getMoeApiService()
    val elfService = ApiGenerator.getElfHApiService()
    val sp = sp(PLAY_LIST_ID_SP_NAME)

    Mood.values().forEach { mood ->
        val playListID = sp.getLong(mood.name, 0L)
        if (playListID != 0L) {
            withSPCache(playListID.toString(), PlayListWrapper::class.java, { moeService.getPlayList(playListID) }, {
                if (playListID == sp.getLong(mood.name, 0L))
                    it.result.tracks?.shuffle()
                    App.playListManager.updatePlayList(mood.name, it.result)
            })
        }
        elfService.getSongListID(mood.name)
            .subscribeOn(Schedulers.io())
            .safeSubscribeBy {
                if (it.status != 200 || it.data == null || it.data.id == playListID) {
                    return@safeSubscribeBy
                }
                sp.edit {
                    putLong(mood.name, it.data.id)
                }
                withSPCache(
                    it.data.id.toString(),
                    PlayListWrapper::class.java,
                    { moeService.getPlayList(it.data.id) },
                    { wrapper ->
                        wrapper.result.tracks?.shuffle()
                        App.playListManager.updatePlayList(mood.name, wrapper.result)
                    })
            }
        Unit
    }
}