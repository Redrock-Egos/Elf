package com.egos.elf.common.net

import com.egos.elf.common.net.api.ElfApiServiceF
import com.egos.elf.common.net.api.ElfApiServiceH
import com.egos.elf.common.net.api.MoeApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ApiGenerator {

    private var moeRetrofit = Retrofit.Builder()
        .baseUrl("http://music.moe.tn/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private var elfRetrofitH = Retrofit.Builder()
        .baseUrl("http://elf.egos.hosigus.tech/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private var elfRetrofitF = Retrofit.Builder()
        .baseUrl("http://120.79.413.238/cqupt/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getMoeApiService(): MoeApiService = moeRetrofit.create(MoeApiService::class.java)

    fun getElfHApiService(): ElfApiServiceH = elfRetrofitH.create(ElfApiServiceH::class.java)

    fun getElfFApiService(): ElfApiServiceF = elfRetrofitF.create(ElfApiServiceF::class.java)

}