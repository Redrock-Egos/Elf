package com.egos.elf.common.bean.moe


import com.google.gson.annotations.SerializedName

data class LyricWrapper(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("lrc")
    val lrc: Lrc? = null
)

data class Lrc(
    @SerializedName("lyric")
    val lyric: String = ""
)


