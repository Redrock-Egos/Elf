package com.egos.elf.common.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.egos.elf.common.bean.moe.PlayList

/**
 * create by:Fxymine4ever
 * time: 2018/11/29
 */
@Database(entities = [PlayList::class], version = 1)
abstract class RecDatabase : RoomDatabase() {
    abstract fun getRecEntryDao(): PlaylistDao

    companion object {
        const val NAME = "REC_DATABASE"
    }
}