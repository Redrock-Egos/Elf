package com.egos.elf.common.utils

import com.egos.elf.App
import com.egos.elf.common.bean.moe.Music
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