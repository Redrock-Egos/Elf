package com.egos.elf.common.bean

import com.egos.elf.common.bean.moe.Music
import com.egos.elf.common.bean.moe.PlayList

/*
 * Created by Cchanges on 2018/11/27
 */
data class PiazzaData(
    val music: Music,
    val time: String,
    val avatarUrl: String
)

fun PiazzaData.toPlayList() : PlayList{
    return PlayList(tracks = mutableListOf(this.music))
}
