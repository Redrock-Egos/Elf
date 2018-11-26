package com.egos.elf.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.egos.elf.common.bean.RecAlbum
import com.egos.elf.common.bean.moe.Music
import com.egos.elf.common.utils.frecyclerview.BaseAdapter
import com.egos.elf.common.utils.frecyclerview.BaseHolder
import kotlinx.android.synthetic.main.item_rv_rec.view.*

/**
 * create by:Fxymine4ever
 * time: 2018/11/26
 */
class RecDetailAdapter(private val mContext: Context,
                       private val mData: MutableList<Music>?,
                       private val mLayoutIds: IntArray) :
        BaseAdapter<Music>(mContext, mData, mLayoutIds) {
    @SuppressLint("SetTextI18n")
    override fun onBinds(holder: BaseHolder, t: MutableList<Music>?, position: Int, viewType: Int) {
        when (viewType) {
            NORMAL -> {
                val parent = holder.itemView
                mData?.let { it ->
                    parent.rec_detail_tv_hot.text = "${(436 - 20*position*0.8).toInt()}"
                    parent.rec_detail_tv_name.text = it[position].name
                    parent.rec_detail_tv_singer.text = it[position].artists!![position].artistName
                    parent.rec_detail_tv_num.text = "0${position + 1}"
                    parent.setOnClickListener {view->
                        //TODO:跳转到播放界面 传入list 和 当前position
                    }
                }
            }
        }
    }

    override fun getItemType(position: Int): Int = NORMAL

}