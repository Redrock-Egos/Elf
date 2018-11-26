package com.egos.elf.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.egos.elf.common.bean.RecAlbum
import com.egos.elf.common.bean.moe.Album
import com.egos.elf.repository.RecRepository
import io.reactivex.annotations.NonNull

/**
 * create by:Fxymine4ever
 * time: 2018/11/25
 */
class RecModel(@NonNull application: Application) : AndroidViewModel(application) {
    private var liveData:LiveData<RecAlbum> = RecRepository.instance.getRecAlbum("rec")
    fun loadData(){
        liveData = RecRepository.instance.getRecAlbum("rec")
    }

    fun getData():LiveData<RecAlbum>{
        return liveData
    }
}