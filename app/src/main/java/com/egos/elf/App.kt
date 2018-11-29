package com.egos.elf

import android.annotation.SuppressLint
import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.danikula.videocache.HttpProxyCacheServer
import com.egos.elf.common.room.ElfDatabase
import com.egos.elf.common.room.RecDatabase
import com.egos.elf.common.utils.PlayListManager

class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set

        private var proxy: HttpProxyCacheServer? = null
        fun getProxy(): HttpProxyCacheServer {
            if (proxy == null) {
                proxy = HttpProxyCacheServer(context)
            }
            return proxy!!
        }

        lateinit var playListManager: PlayListManager
            private set

        lateinit var elfDatabase: ElfDatabase
            private set

        lateinit var recDatabase: RecDatabase
            private set
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = base
        playListManager = PlayListManager()
        elfDatabase = Room.databaseBuilder(base, ElfDatabase::class.java, ElfDatabase.NAME).build()
        recDatabase = Room.databaseBuilder(base,RecDatabase::class.java,RecDatabase.NAME).build()
    }
}