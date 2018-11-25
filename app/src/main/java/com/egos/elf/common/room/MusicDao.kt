package com.egos.elf.common.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.egos.elf.common.bean.moe.Music

@Dao
interface MusicDao {
    @Insert()
    fun insertMusic(e: Music)
}