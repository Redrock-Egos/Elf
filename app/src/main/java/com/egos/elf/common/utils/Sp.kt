package com.egos.elf.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.egos.elf.App
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable


private val gson = Gson()
private const val DEFAULT_SP_NAME = "ElfDefault"

val defaultSp get() = App.context.sp(DEFAULT_SP_NAME)

fun Context.sp(name: String): SharedPreferences = getSharedPreferences(name, Context.MODE_PRIVATE)

fun sp(name: String) = App.context.sp(name)

fun SharedPreferences.edit(modify: SharedPreferences.Editor.() -> Unit) = edit().apply(modify).apply()

fun <T> getBeanFromSP(keyName: String, clazz: Class<T>, spName: String = DEFAULT_SP_NAME): T? {
    try {
        val json = sp(spName).getString(keyName, null)
        json ?: return null
        return gson.fromJson(json, clazz)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun <T> putBeanToSP(keyName: String, bean: T, spName: String = DEFAULT_SP_NAME) =
    sp(spName).edit { putString(keyName, gson.toJson(bean)) }

fun <T> withSPCache(
    keyName: String, clazz: Class<T>, observable: () -> Observable<T>,
    onGetBean: (T) -> Unit, fail: (Throwable) -> Unit = { it.printStackTrace() },
    spName: String = DEFAULT_SP_NAME
) = Observable.create<String> {
    it.onNext(sp(spName).getString(keyName, "") ?: "")
}.doOnNext { json ->
    observable()
        .filter { gson.toJson(it) != json }
        .doOnNext { putBeanToSP(keyName, it, spName) }
        .setSchedulers()
        .subscribe(onGetBean, fail)
}.map {
    if (it.isNotBlank()) {
        gson.fromJson(it, clazz)
    } else {
        null
    }
}.setSchedulers()
    .onErrorReturn { null }
    .subscribe({ it?.let(onGetBean) }, {
        (it as? JsonSyntaxException) ?: (it as? NullPointerException) ?: fail(it)
    })
