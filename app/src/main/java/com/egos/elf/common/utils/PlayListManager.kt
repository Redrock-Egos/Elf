package com.egos.elf.common.utils

import android.content.SharedPreferences
import com.egos.elf.common.bean.Mood
import com.egos.elf.common.bean.moe.PlayList

class PlayListManager {
    companion object {
        const val DEFAULT_PLAY_LIST_KEY = "DEFAULT"
        private const val SEQUENCE_SP_NAME = "PLAY_LIST_SEQUENCE"
    }

    private val playlistMap = mutableMapOf<String, PlayList>()
    private val keySequence = mutableListOf<String>()

    var currentListChangeListener: (() -> Unit)? = null

    private val sp: SharedPreferences

    init {
        Mood.values().forEach {
            playlistMap[it.name] = PlayList()
        }
        playlistMap[DEFAULT_PLAY_LIST_KEY] = PlayList()

        sp = sp(SEQUENCE_SP_NAME)
        sp.getString("0", null) ?: sp.edit {
            repeat(4) {
                putString("$it", Mood.values()[it].name)
            }
        }
        repeat(4) {
            keySequence.add(sp.getString("$it", null) ?: Mood.values()[it].name)
        }
    }

    fun updatePlayList(key: String, playList: PlayList) {
        playList.tracks?.shuffle()
        playlistMap[key] = playList
        if (key == keySequence[0]) {
            currentListChangeListener?.invoke()
        }
    }

    fun updateKeySequence(select: String) {
        keySequence.remove(select)
        if (keySequence[0] == DEFAULT_PLAY_LIST_KEY) {
            keySequence[0] = select
        } else {
            keySequence.add(0, select)
        }
        if (select != DEFAULT_PLAY_LIST_KEY) {
            sp.edit {
                keySequence.forEachIndexed { index, s ->
                    putString(index.toString(), s)
                }
            }
        }
    }

    fun useKeySequence(use: (key: String) -> Unit) {
        keySequence.forEach(use)
    }

    fun getKeySequence(pos: Int) = keySequence[pos]

    fun getCurrentPlayList() = playlistMap[keySequence[0]]

}