package com.egos.elf.common.net.api

import com.egos.elf.common.bean.elf.BaseElfBean
import com.egos.elf.common.bean.elf.ID
import com.egos.elf.common.bean.elf.Version
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
    //todo FS的接口
}