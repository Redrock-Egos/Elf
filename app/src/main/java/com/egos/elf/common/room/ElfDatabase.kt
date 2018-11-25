package com.egos.elf.common.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.egos.elf.common.bean.moe.Music

@Database(entities = [Music::class], version = 1)
abstract class ElfDatabase : RoomDatabase() {
    abstract fun getEntryDao(): MusicDao

    companion object {
        const val NAME = "ELF_DATABASE"
    }
}