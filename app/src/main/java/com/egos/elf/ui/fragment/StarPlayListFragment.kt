package com.egos.elf.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egos.elf.R
import com.egos.elf.common.utils.startMusicDetailActivity
import kotlinx.android.synthetic.main.fragment_star_play_list.view.*

class StarPlayListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_star_play_list, container, false)
        v.apply {
            rv_star_list.layoutManager = LinearLayoutManager(context)
//           todo 收藏歌单的List<PlayList> rv_star_list.adapter = StarPlayListAdapter()
            iv_more.setOnClickListener {
                startMusicDetailActivity(context)
            }
        }
        return v
    }

}