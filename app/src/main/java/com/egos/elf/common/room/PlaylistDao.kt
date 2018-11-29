package com.egos.elf.common.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.egos.elf.common.bean.moe.PlayList

/**
 * create by:Fxymine4ever
 * time: 2018/11/29
 */
@Dao
abstract interface PlaylistDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylist(list: PlayList)

    @Query("SELECT * FROM playlist")
    fun selectAllList(): List<PlayList>

    @Query("DELETE FROM playlist WHERE id = :id")
    fun deletePlaylist(id: Long)
}