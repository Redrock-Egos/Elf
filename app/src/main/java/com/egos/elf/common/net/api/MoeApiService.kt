package com.egos.elf.common.net.api

import com.egos.elf.common.bean.moe.LyricWrapper
import com.egos.elf.common.bean.moe.PlayListWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MoeApiService {
    @GET("playlist/detail")
    fun getPlayList(@Query("id") albumListId: Long): Observable<PlayListWrapper>

    @GET("/lyric")
    fun getLyric(@Query("id") musicId: Long): Observable<LyricWrapper>
}