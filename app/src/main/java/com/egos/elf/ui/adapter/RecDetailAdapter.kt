package com.egos.elf.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import com.egos.elf.common.bean.moe.Music
import com.egos.elf.common.bean.moe.PlayListWrapper
import com.egos.elf.common.utils.ToastUtils
import com.egos.elf.common.utils.frecyclerview.BaseAdapter
import com.egos.elf.common.utils.frecyclerview.BaseHolder
import kotlinx.android.synthetic.main.item_rv_rec.view.*
import org.jetbrains.anko.textColor

/**
 * create by:Fxymine4ever
 * time: 2018/11/26
 */
class RecDetailAdapter(private val mContext: Context,
                       private val mData: MutableList<Music>?,
                       private val mLayoutIds: IntArray) :
        BaseAdapter<Music>(mContext, mData, mLayoutIds) {
    private lateinit var wrapper: PlayListWrapper

    fun setWrapper(wrapper: PlayListWrapper) {
        this.wrapper = wrapper
    }

    @SuppressLint("SetTextI18n")
    override fun onBinds(holder: BaseHolder, t: MutableList<Music>?, position: Int, viewType: Int) {
        when (viewType) {
            NORMAL -> {
                val parent = holder.itemView
                mData?.let { it ->
                    parent.rec_detail_tv_hot.text = "${(436 - 20 * position * 0.4).toInt()}"
                    parent.rec_detail_tv_name.text = it[position].name
                    parent.rec_detail_tv_singer.text = it[position].artists!![0].artistName
                    parent.rec_detail_tv_num.text = "0${position + 1}"
                    parent.setOnClickListener { view ->
                        ToastUtils.show("position")
//                        PlayListManager().updatePlayList(PlayListManager.DEFAULT_PLAY_LIST_KEY,wrapper.result)
                        //TODO:跳转到播放界面
                    }
                    if (position < 3) {
                        parent.rec_detail_tv_num.textColor = Color.parseColor("#EA6A61")
                    }
                }
            }
        }
    }

    override fun getItemType(position: Int): Int {
        getData()?.let { it ->
            return if (position != it.size) NORMAL else FOOTER
        }
        return FOOTER
    }
}