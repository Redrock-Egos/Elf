package com.egos.elf.common.bean.moe

import android.arch.persistence.room.*
import com.egos.elf.common.room.TypeConverterHelper
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder
import java.util.*

data class PlayListWrapper(
    @SerializedName("result")
    val result: PlayList,
    @SerializedName("code")
    val code: Int = 0
)

data class Artist(
    @SerializedName("name")
    val artistName: String = "",
    @SerializedName("id")
    val artistId: Long = 0L
)

@Entity(tableName = "music")
@TypeConverters(TypeConverterHelper::class)
data class Music(
    @SerializedName("id")
    @PrimaryKey()
    var id: Long = 0L,
    @SerializedName("duration")
    var duration: Int = 0,
    @SerializedName("artists")
    var artists: List<Artist>? = null,
    @SerializedName("album")
    @Embedded
    var album: Album? = null,
    @SerializedName("name")
    var name: String = "",
    @Expose(serialize = false, deserialize = false)
    var like: Boolean = false,
    @Expose(serialize = false, deserialize = false)
    var star: Boolean = false,
    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "star_date")
    var starDate: Long = 0L,
    @Expose(serialize = false, deserialize = false)
    var category: String = ""
) {
    val artistNameStr: String
        get() {
            val sb = StringBuilder()
            artists?.forEach {
                sb.append(it).append("/")
            }
            return sb.toString()
        }
}

data class Album(
    @SerializedName("blurPicUrl")
    @ColumnInfo(name = "blur_pic_url")
    var blurPicUrl: String = ""
)

data class Creator(
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("userId")
    val userId: Long = 0L,
    @SerializedName("gender")
    val gender: Int = 0,
    @SerializedName("signature")
    val signature: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("detailDescription")
    val detailDescription: String = "",
    @SerializedName("backgroundUrl")
    val backgroundUrl: String = "",
    @SerializedName("avatarUrl")
    val avatarUrl: String = ""
)

data class PlayList(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("trackCount")
    val trackCount: Int = 0,
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("totalDuration")
    val totalDuration: Int = 0,
    @SerializedName("creator")
    val creator: Creator? = null,
    @SerializedName("commentThreadId")
    val commentThreadId: String = "",
    @SerializedName("updateTime")
    val updateTime: Long = 0,
    @SerializedName("userId")
    val userId: Int = 0,
    @SerializedName("tracks")
    val tracks: MutableList<Music>? = null,
    @SerializedName("coverImgUrl")
    val coverImgUrl: String = "",
    @SerializedName("name")
    val name: String = ""
)