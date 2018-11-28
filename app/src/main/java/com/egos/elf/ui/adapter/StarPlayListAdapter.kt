package com.egos.elf.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.egos.elf.R
import com.egos.elf.common.bean.moe.PlayList
import kotlinx.android.synthetic.main.item_rv_star_list.view.*
import kotlinx.android.synthetic.main.item_star_item_music.view.*

class StarPlayListAdapter(private val list: List<PlayList>) : RecyclerView.Adapter<StarPlayListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_star_list, parent, false))


    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plist = list[position]
        holder.itemView.apply {
            tv_playlist_name.text = plist.name
            Glide.with(this).load(plist.coverImgUrl).into(iv_img)
            plist.tracks[0].let {
                Glide.with(this).load(it.album?.blurPicUrl).into(riv_music_img)
                tv_music_name.text = it.name
                tv_singer_name.text = it.artistNameStr
            }
            btn_play.setOnClickListener {
                //todo 跳转歌单详情页面查看列表
            }
            setOnClickListener {
                //todo 跳转推荐页面查看歌单详情
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}