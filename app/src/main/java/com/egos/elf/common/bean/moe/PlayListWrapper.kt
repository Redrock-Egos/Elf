package com.egos.elf.common.bean.moe

import android.arch.persistence.room.*
import com.egos.elf.common.bean.Mood
import com.egos.elf.common.room.RecTypeCOnverterHelper
import com.egos.elf.common.room.TypeConverterHelper
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlayListWrapper(
    @SerializedName("result")
    var result: PlayList,
    @SerializedName("code")
    var code: Int = 0
)

data class Artist(
    @SerializedName("name")
    var artistName: String = "",
    @SerializedName("id")
    var artistId: Long = 0L
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
    var category: String = "",
    @Expose(serialize = false, deserialize = false)
    var mood: Mood = Mood.HAPPY
) {
    val artistNameStr: String
        get() {
            var sb = StringBuilder()
            artists?.forEach {
                sb.append(it.artistName).append("/")
            }
            return sb.substring(0, sb.length - 1).toString()
        }
}

data class Album(
    @SerializedName("blurPicUrl")
    @ColumnInfo(name = "blur_pic_url")
    var blurPicUrl: String = ""
)

data class Creator(
    @SerializedName("nickname")
    var nickname: String = "",
    @ColumnInfo(name = "creator_userId")
    @SerializedName("userId")
    var userId: Long = 0L,
    @SerializedName("gender")
    var gender: Int = 0,
    @SerializedName("signature")
    var signature: String = "",
    @ColumnInfo(name = "creator_description")
    @SerializedName("description")
    var description: String = "",
    @SerializedName("detailDescription")
    var detailDescription: String = "",
    @SerializedName("backgroundUrl")
    var backgroundUrl: String = "",
    @SerializedName("avatarUrl")
    var avatarUrl: String = ""
)

@Entity(tableName = "playlist")
@TypeConverters(RecTypeCOnverterHelper::class)
data class PlayList(
    @SerializedName("description")
    var description: String = "",
    @SerializedName("id")
    @PrimaryKey()
    var id: Long = 0L,
    @SerializedName("totalDuration")
    var totalDuration: Int = 0,
    @SerializedName("creator")
    var creator: Creator? = null,
    @SerializedName("commentThreadId")
    var commentThreadId: String = "",
    @SerializedName("updateTime")
    var updateTime: Long = 0,
    @SerializedName("userId")
    var userId: Int = 0,
    @SerializedName("tracks")
    var tracks: MutableList<Music> = mutableListOf(),
    @SerializedName("coverImgUrl")
    var coverImgUrl: String = "",
    @SerializedName("name")
    var name: String = ""
)