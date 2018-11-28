package com.egos.elf.ui.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.egos.elf.R
import com.egos.elf.common.bean.PiazzaData
import com.egos.elf.common.bean.moe.PlayList
import com.egos.elf.common.bean.toPlayList
import kotlinx.android.synthetic.main.item_rv_piazza.view.*

/*
 * Created by Cchanges on 2018/11/27
 */
class PiazzaAdapter(
    private var commentList: MutableList<PiazzaData>,
    private var listener: (list:PlayList) -> Unit
) : RecyclerView.Adapter<PiazzaAdapter.PiazzaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PiazzaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_piazza, parent, false)
        return PiazzaViewHolder(view)
    }

    override fun getItemCount() = commentList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PiazzaViewHolder, position: Int) {
        val currentMusic = commentList[position].music
        holder.itemView.apply {
            Glide.with(context)
                .asBitmap()
                .load(currentMusic.album?.blurPicUrl)
                .into(riv_music_bg_piazza)
            Glide.with(context)
                .asBitmap()
                .load(commentList[position].avatarUrl)
                .into(riv_avatar)
            tv_song_pizza.text = currentMusic.name
            tv_singer_piazza.text = currentMusic.artistNameStr
            tv_time_piazza.text = commentList[position].time + "前"
            ibtn_piazza_playing.setOnClickListener {
                listener(commentList[position].toPlayList())
            }
        }

    }

    fun setData(list: MutableList<PiazzaData>) {
        commentList = list
        notifyDataSetChanged()
    }

    inner class PiazzaViewHolder(view: View) : RecyclerView.ViewHolder(view)
}