package com.egos.elf.common.room

import android.arch.persistence.room.TypeConverter
import com.egos.elf.common.bean.Mood
import com.egos.elf.common.bean.moe.Artist
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverterHelper {
    private val gson = Gson()

    @TypeConverter
    fun boolean2int(boolean: Boolean) = if (boolean) 1 else 0

    @TypeConverter
    fun int2boolean(int: Int) = int == 1

    @TypeConverter
    fun artists2string(a: List<Artist>?) = gson.toJson(a)

    @TypeConverter
    fun string2artists(json: String): List<Artist>? = gson.fromJson(json, object : TypeToken<List<Artist>>() {}.type)

    @TypeConverter
    fun mood2string(mood: Mood) = mood.name

    @TypeConverter
    fun string2mood(str: String) = Mood.valueOf(str)

}