package com.egos.elf.common.utils

import com.egos.elf.App
import com.egos.elf.common.bean.moe.Music
import com.egos.elf.common.bean.moe.PlayList
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun selectStarList() = Observable.create<List<Music>?> {
    it.onNext(App.elfDatabase.getEntryDao().selectStarList())
}.setSchedulers()

fun selectMusicById(id: Long) = Observable.create<Music?> {
    it.onNext(App.elfDatabase.getEntryDao().selectMusicById(id))
}.setSchedulers()

fun insertMusic(music: Music) {
    Schedulers.io().scheduleDirect {
        App.elfDatabase.getEntryDao().insertMusic(music)
    }
}

fun selectRecList() = Observable.create<List<PlayList>?> {
    it.onNext(App.recDatabase.getRecEntryDao().selectAllList())
}.setSchedulers()

fun deletePlaylistByid(id: Long){
    Schedulers.io().scheduleDirect {
        App.recDatabase.getRecEntryDao().deletePlaylist(id)
    }
}

fun insertRecPlaylist(playlist: PlayList) {
    Schedulers.io().scheduleDirect {
        App.recDatabase.getRecEntryDao().insertPlaylist(playlist)
    }
}