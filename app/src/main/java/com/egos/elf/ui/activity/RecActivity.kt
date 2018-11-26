package com.egos.elf.ui.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.egos.elf.R
import com.egos.elf.common.base.BaseNoNeedListenActivity
import com.egos.elf.common.bean.RecAlbum
import com.egos.elf.common.utils.defaultSp
import com.egos.elf.common.utils.edit
import com.egos.elf.common.utils.sp
import com.egos.elf.model.RecModel
import kotlinx.android.synthetic.main.activity_rec.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.defaultSharedPreferences

/**
 * 日推主界面
 */
class RecActivity : BaseNoNeedListenActivity() {
    override val resId: Int
        get() = R.layout.activity_rec
    private lateinit var model: RecModel
    private val sp:SharedPreferences = defaultSp
    private lateinit var data: RecAlbum

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
            }
        }
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun loadData(data: RecAlbum) {
        if (data.code == 200) {
            Glide.with(this).load(data.result[1].bg_url).into(rec_iv_bg)
            Glide.with(this).load(data.result[1].bg_url).into(rec_iv_album_bg)
            rec_tv_title.text = data.result[1].title
            rec_tv_content.text = data.result[1].content
            rec_iv_album_title.text = data.result[1].title
            rec_iv_album_owner.text = data.result[1].owner
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
            Log.d("fxy",sp.getBoolean("isRecLike", false).toString())
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
                it.backgroundResource = R.drawable.ic_star_on
            } else {
                sp.edit {
                    putBoolean("isRecStar", false)
                }
                it.backgroundResource = R.drawable.ic_star_off
            }
        }
        rec_btn_play.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("albumId",data.result[1].id)
            val intent = Intent(this, RecDetailActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}
