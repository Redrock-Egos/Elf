package com.egos.elf.ui.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.egos.elf.App
import com.egos.elf.R
import com.egos.elf.common.base.BaseNoNeedListenActivity
import com.egos.elf.common.bean.moe.PlayListWrapper
import com.egos.elf.common.utils.defaultSp
import com.egos.elf.common.utils.deletePlaylistByid
import com.egos.elf.common.utils.edit
import com.egos.elf.common.utils.insertRecPlaylist
import com.egos.elf.model.RecModel
import kotlinx.android.synthetic.main.activity_rec.*
import org.jetbrains.anko.backgroundResource

/**
 * 日推主界面
 */
class RecActivity : BaseNoNeedListenActivity() {
    override val resId: Int
        get() = R.layout.activity_rec
    private lateinit var model: RecModel
    private val sp: SharedPreferences = defaultSp
    private lateinit var data: PlayListWrapper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        common_toolbar.init("日推")
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        model = ViewModelProviders.of(this).get(RecModel::class.java)
        model.loadData()
        model.getData().observeForever { it ->
            if (it != null) {
                loadData(it)
                App.playListManager.updatePlayList("temp",it.result)
            }
        }
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun loadData(data: PlayListWrapper) {
        if (data.code == 200) {
            Log.d("fxy",data.toString())
            Glide.with(this).load(data.result.coverImgUrl).into(rec_iv_bg)
            Glide.with(this).load(data.result.tracks[0].album!!.blurPicUrl).into(rec_iv_album_bg)
            rec_tv_title.text = data.result.name
            rec_tv_content.text = data.result.description
            rec_iv_album_title.text = data.result.tracks[0].name
            rec_iv_album_owner.text = data.result.tracks[0].artists!![0].artistName
            this.data = data
        }
    }

    private fun initView() {
        if (sp.getBoolean("isRecLike", false)) {
            rec_btn_like.backgroundResource = R.drawable.ic_like_on
        }
        if (sp.getBoolean("isRecStar", false)) {
            rec_btn_star.backgroundResource = R.drawable.ic_star_on
        }

        rec_btn_like.setOnClickListener {
            if (!sp.getBoolean("isRecLike", false)) {
                sp.edit {
                    putBoolean("isRecLike", true)
                }
                it.backgroundResource = R.drawable.ic_like_on
            } else {
                sp.edit {
                    putBoolean("isRecLike", false)
                }
                it.backgroundResource = R.drawable.ic_like_off
            }
        }
        rec_btn_star.setOnClickListener {
            if (!sp.getBoolean("isRecStar", false)) {
                sp.edit {
                    putBoolean("isRecStar", true)
                }
                insertRecPlaylist(data.result)
                it.backgroundResource = R.drawable.ic_star_on
            } else {
                sp.edit {
                    putBoolean("isRecStar", false)
                }
                deletePlaylistByid(data.result.id)
                it.backgroundResource = R.drawable.ic_star_off
            }
        }
        rec_btn_play.setOnClickListener {
            startActivity(Intent(this@RecActivity,RecDetailActivity::class.java))
        }
    }
}
