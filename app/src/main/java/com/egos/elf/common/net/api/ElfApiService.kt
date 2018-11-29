package com.egos.elf.common.net.api

import com.egos.elf.common.bean.elf.BaseElfBean
import com.egos.elf.common.bean.elf.ID
import com.egos.elf.common.bean.elf.Version
import com.egos.elf.common.bean.moe.PlayListWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ElfApiServiceH {
    @GET("getSongListID.php")
    fun getSongListID(@Query("type") type: String): Observable<BaseElfBean<ID>>

    @GET("getLastVersion.php")
    fun getLastVersion(): Observable<BaseElfBean<Version>>
}

interface ElfApiServiceF {
    @GET("getRecAlbum")
    fun getRecAlbum(@Query("mood") mood:String):Observable<PlayListWrapper>
}