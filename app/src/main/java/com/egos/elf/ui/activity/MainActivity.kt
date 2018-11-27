package com.egos.elf.ui.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.AppCompatImageView
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.egos.elf.App
import com.egos.elf.R
import com.egos.elf.common.base.BaseActivity
import com.egos.elf.common.bean.Mood
import com.egos.elf.common.net.ApiGenerator
import com.egos.elf.common.utils.*
import com.egos.elf.ui.widget.MusicInfoLayout
import com.egos.elf.ui.widget.MusicInfoLayout.Companion.STATE_PAUSE
import com.egos.elf.ui.widget.MusicInfoLayout.Companion.STATE_PLAYING
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_main.*
import kotlinx.android.synthetic.main.include_card.view.*
import kotlinx.android.synthetic.main.include_disk.*
import kotlinx.android.synthetic.main.include_lyrics.view.*
import kotlinx.android.synthetic.main.include_toolbar_backable.*

class MainActivity : BaseActivity() {

    private val discView by lazy { MusicInfoLayout(this) }
    private val lyricView by lazy { View.inflate(this, R.layout.include_lyrics, null) }
    private val animator by lazy {
        ObjectAnimator.ofFloat(fl_container, "alpha", 1f, 0f, 1f)
            .apply {
                duration = 800
                interpolator = LinearInterpolator()
            }
    }

    private var x1 = 0f
    private var x2 = 0f
    private var showTime = 0.0
    private var unhappyTimes = 0
    private var isPicture = true
    private var isCommendShow = false
    private var isDialogShow = false

    private val TAG = "MainActivity"

    override val resId: Int
        get() = R.layout.activity_main

    override val needSlide: Boolean
        get() = false

    override fun onActivate() {
        musicControlBinder?.getCurrentTrack()?.apply {
            discView.initMusicTextData(this)
            Glide.with(this@MainActivity).load(album?.blurPicUrl).into(object : CustomViewTarget<AppCompatImageView,Drawable>(disk_view) {
                override fun onResourceCleared(placeholder: Drawable?) = Unit

                override fun onLoadFailed(errorDrawable: Drawable?) = Unit

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    disk_view.upDatePicture(resource)
                }

            })
            ApiGenerator.getMoeApiService()
                .getLyric(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribeBy(onError = { e ->
                    e.printStackTrace()
                    ToastUtils.show("暂未获取数据:>")
                    lyricView.lrc_main.setLabel("获取歌词失败:(")
                }) { lyric ->
                    if (lyric.lrc != null) {
                        lyricView.lrc_main.loadLrc(lyric.lrc.lyric)
                    } else {
                        ToastUtils.show("暂时没有歌词呢:<")
                        lyricView.lrc_main.setLabel("暂时没有歌词呢:(")
                    }
                }
        }
    }

    override fun onPlayStart() {
        discView.apply {
            if (currentState != STATE_PLAYING) {
                currentState = STATE_PAUSE
                changeDiskState()
            }
        }
        isCommendShow = (Math.random() + 0.5).toInt() == 1
        if (isCommendShow) {
            showTime = Math.random() * 0.01 + 0.03
        }
        if (unhappyTimes > 5 && Mood.valueOf(App.playListManager.getKeySequence(0)) == Mood.UNHAPPY && !isDialogShow) {
            val view = View.inflate(this, R.layout.include_card, null).apply {
                tv_card_text.text = "还是很沮丧吗？来点开心的吧！"
                btn_card_sure.setOnClickListener {

                }
                btn_card_cancel.setOnClickListener {
                    dl_main.removeView(this)
                }
            }
            val param = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(
                    (45 / 375.0 * screenWidth).toInt(),
                    (440 / 667.0 * screenHeight).toInt(),
                    (45 / 375.0 * screenWidth).toInt(),
                    (100 / 667.0 * screenHeight).toInt()
                )
            }
            dl_main.addView(view, param)
            isDialogShow = true
        }
        if (Mood.valueOf(App.playListManager.getKeySequence(0)) == Mood.UNHAPPY) {
            unhappyTimes += 1
        }
    }

    override fun onPlayPause() {
        discView.apply {
            currentState = STATE_PLAYING
            changeDiskState()
        }
    }

    override fun onPlayStop() = Unit

    @SuppressLint("SetTextI18n")
    override fun onPlayProgressUpdate(progress: Int) {
        lyricView.lrc_main.updateTime(progress.toLong())
        var view: View? = null
        if (isCommendShow && progress.toDouble() > showTime * (musicControlBinder?.getDuration()
                ?: 1)
        ) {
            val time = (Math.random() * 10 + 2).toInt()
            view = View.inflate(this, R.layout.include_card, null).apply {
                btn_card_cancel.gone()
                btn_card_sure.gone()
                tv_card_text.text = "在${time}分钟前，有人发表了评论"
                ObjectAnimator.ofFloat(this, "alpha", 1f, 0f).apply {
                    duration = 5000
                    start()
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationEnd(animation: Animator?) = this@MainActivity.dl_main.removeView(view)
                        override fun onAnimationRepeat(animation: Animator?) = Unit
                        override fun onAnimationCancel(animation: Animator?) = Unit
                        override fun onAnimationStart(animation: Animator?) = Unit
                    })
                }
            }
            val param = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(
                    (45 / 375.0 * screenWidth).toInt(),
                    (37 / 667.0 * screenHeight).toInt(),
                    (45 / 375.0 * screenWidth).toInt(),
                    (587 / 667.0 * screenHeight).toInt()
                )
            }
            this@MainActivity.dl_main.addView(view, param)
            isCommendShow = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fl_container.addView(discView)
        discView.changeDiskState()
        fl_container.setOnClickListener { changeView() }

        common_toolbar.init(" ", R.drawable.ic_setting, View.OnClickListener { _ ->
            if (!dl_main.isDrawerOpen(nv_setting)) {
                dl_main.openDrawer(nv_setting)

                //      todo 修改个人信息
                cim_avatar.setOnClickListener {

                }

                tv_nickname.setOnClickListener {

                }
            }
        })
        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        //todo：前往不同的页面
//      音乐详情
        ibtn_more_main.setOnClickListener {
            startMusicDetailActivity(this@MainActivity)
        }

        nv_setting.setNavigationItemSelectedListener {
            when (it.title) {
                resources.getString(R.string.daily_recommend) -> {
                }
                resources.getString(R.string.setting) -> {
                }

                resources.getString(R.string.comments_plaza) -> startActivity(
                    Intent(
                        this@MainActivity,
                        PiazzaActivity::class.java
                    )
                )

                resources.getString(R.string.my_collection) -> {
                    startShowStarActivity(this@MainActivity)
                }
            }
            dl_main.closeDrawer(nv_setting)
            true
        }


    }

    override fun onResume() {
        smv_main.updateRes()
        super.onResume()
    }

    private fun changeView() {
        var isUpdate = false
        if (!isPicture) {
            isPicture = true
            toolbar_title.text = " "
            animator.apply {
                addUpdateListener {
                    if ((it.animatedValue as Float) < 0.02 && !isUpdate) {
                        isUpdate = true
                        fl_container.removeAllViews()
                        fl_container.addView(discView)
                    }
                }
                start()
            }

        } else {
            isPicture = false
            toolbar_title.text = musicControlBinder?.getCurrentTrack()?.name
            animator.apply {
                addUpdateListener {
                    if ((it.animatedValue as Float) < 0.02 && !isUpdate) {
                        isUpdate = true
                        fl_container.removeAllViews()
                        fl_container.addView(lyricView)
                    }
                }
                start()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (!isPicture)
            return super.dispatchTouchEvent(ev)

        if (ev.action == MotionEvent.ACTION_DOWN) {
            x1 = ev.x
        }
        if (ev.action == MotionEvent.ACTION_UP) {
            x2 = ev.x
            when {
                x1 - x2 > screenWidth / 3f -> {
                    musicControlBinder?.playNext()
                    return true
                }
                x2 - x1 > screenWidth / 3f -> {
                    musicControlBinder?.playPrev()
                    return true
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}
