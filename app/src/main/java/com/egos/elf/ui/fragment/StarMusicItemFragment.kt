package com.egos.elf.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egos.elf.App

import com.egos.elf.R
import com.egos.elf.common.bean.Mood
import com.egos.elf.common.bean.moe.Creator
import com.egos.elf.common.bean.moe.Music
import com.egos.elf.common.bean.moe.PlayList
import com.egos.elf.common.utils.PlayListManager
import com.egos.elf.common.utils.safeSubscribeBy
import com.egos.elf.common.utils.selectStarList
import com.egos.elf.common.utils.startMusicDetailActivity
import com.egos.elf.ui.activity.ShowStarActivity
import com.egos.elf.ui.adapter.StarItemListAdapter
import kotlinx.android.synthetic.main.fragment_star_music_item.view.*

class StarMusicItemFragment : Fragment() {

    private var currentPlayList: PlayList = makePlayList()
    private val moodMap: MutableMap<Mood, List<Music>> = mutableMapOf()
    private var adapter: StarItemListAdapter = StarItemListAdapter(currentPlayList) { list, pos ->
        (context as? ShowStarActivity)?.onMusicSelect(makePlayList(list), pos)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectStarList().safeSubscribeBy { list ->
            list ?: return@safeSubscribeBy
            list.groupBy {
                it.mood
            }.forEach { (k, v) ->
                moodMap[k] = v
            }
            App.playListManager.apply {
                var key = getKeySequence(0)
                if (key == PlayListManager.DEFAULT_PLAY_LIST_KEY) {
                    key = getKeySequence(1)
                }
                currentPlayList.tracks.clear()
                currentPlayList.tracks.addAll(moodMap[Mood.valueOf(key)] ?: listOf())
            }
            adapter.refresh()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_star_music_item, container, false)
        v.apply {
            iv_more.setOnClickListener {
                startMusicDetailActivity(context)
            }
            rv_star_item.layoutManager = LinearLayoutManager(context)
            rv_star_item.adapter = adapter
        }
        (context as? ShowStarActivity)?.apply {
            activateListener = {
                App.playListManager.apply {
                    var key = getKeySequence(0)
                    if (key == PlayListManager.DEFAULT_PLAY_LIST_KEY) {
                        key = getKeySequence(1)
                    }
                    currentPlayList.tracks.clear()
                    currentPlayList.tracks.addAll(moodMap[Mood.valueOf(key)] ?: listOf())
                }
                adapter.refresh()
            }
        }
        return v
    }

    private fun makePlayList(tracks: List<Music>? = null) = PlayList(
        "收藏歌单",
        creator = Creator("我"),
        name = "收藏歌单",
        tracks = tracks?.toMutableList() ?: mutableListOf()
    )

}
