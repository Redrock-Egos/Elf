package com.egos.elf.common.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.egos.elf.common.bean.moe.Music

@Dao
interface MusicDao {
    @Insert(onConflict = REPLACE)
    fun insertMusic(music: Music)

    @Update(onConflict = REPLACE)
    fun updateMusic(music: Music)

    @Query("SELECT * FROM music WHERE id = :id")
    fun selectMusicById(id: Long): Music

    @Query("SELECT * FROM music WHERE star = 1")
    fun selectStarList(): List<Music>

    @Query("SELECT star FROM music WHERE id = :id")
    fun selectStarById(id: Long): Boolean

    @Query("SELECT `like` FROM music WHERE id = :id")
    fun selectLikeById(id: Long): Boolean
}