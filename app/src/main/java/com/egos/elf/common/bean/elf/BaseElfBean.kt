package com.egos.elf.common.bean.elf

import com.google.gson.annotations.SerializedName

data class BaseElfBean<T>(
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 200
)