package com.egos.elf.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.egos.elf.R
import com.egos.elf.common.bean.moe.Music
import com.egos.elf.common.bean.moe.PlayList
import kotlinx.android.synthetic.main.item_rv_star_item.view.*
import kotlinx.android.synthetic.main.item_star_item_music.view.*
import kotlinx.android.synthetic.main.item_star_item_title.view.*
import org.jetbrains.anko.collections.forEachWithIndex
import java.util.*

class StarItemListAdapter(
    private val playList: PlayList,
    private val onMusicSelect: (playList: List<Music>, pos: Int) -> Unit
) : RecyclerView.Adapter<StarItemListAdapter.ViewHolder>() {
    private class ListData(
        val date: String,
        val stars: Map<String, List<Music>>
    )

    private val comparator = Comparator<String> { s1, s2 ->
        if (s1 == "默认收藏") {
            return@Comparator 1
        } else if (s2 == "默认收藏") {
            return@Comparator -1
        }
        return@Comparator s1.compareTo(s2)
    }

    private var musicList: List<ListData> =
        playList.tracks?.groupBy { DateFormat.format("yyyy.MM.dd", Date(it.starDate)).toString() }
            ?.map {
                ListData(
                    it.key,
                    it.value.groupBy { d -> d.category }
                        .toSortedMap(comparator)
                )
            } ?: listOf()

    fun refresh() {
        musicList = playList.tracks?.groupBy { DateFormat.format("yyyy.MM.dd", Date(it.starDate)).toString() }
            ?.map {
                ListData(
                    it.key,
                    it.value.groupBy { d -> d.category }
                        .toSortedMap(comparator)
                )
            } ?: listOf()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_rv_star_item, p0, false))

    override fun getItemCount() = musicList.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        musicList[p1].apply {
            p0.itemView.initView(date, stars)
        }
    }

    private fun View.initView(date: String, stars: Map<String, List<Music>>) {
        ll_container.apply {
            if (childCount > 1) {
                val view = getChildAt(0)
                removeAllViews()
                addView(view)
            }
        }

        tv_date.text = date
        val inflater = LayoutInflater.from(context)
        val g = Glide.with(context)

        stars.forEach { (describe, list) ->
            ll_container.addView(inflater.inflate(R.layout.item_star_item_title, ll_container, false).apply {
                tv_describe.text = describe
            })
            list.forEachWithIndex { i, it ->
                ll_container.addView(inflater.inflate(R.layout.item_star_item_music, ll_container, false).apply {
                    g.load(it.album?.blurPicUrl).into(riv_music_img)
                    tv_music_name.text = it.name
                    tv_singer_name.text = it.artistNameStr
                    setOnClickListener { _ ->
                        onMusicSelect(list, i)
                    }
                })
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

