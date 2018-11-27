package com.egos.elf.ui.activity

import android.os.Bundle
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.egos.elf.App
import com.egos.elf.R
import com.egos.elf.common.base.BaseActivity
import com.egos.elf.common.net.ApiGenerator
import com.egos.elf.common.utils.ToastUtils
import com.egos.elf.common.utils.insertMusic
import com.egos.elf.common.utils.safeSubscribeBy
import com.egos.elf.common.utils.selectMusicById
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_music_detail.*
import java.text.DecimalFormat
import java.util.*

class MusicDetailActivity : BaseActivity() {
    override fun onActivate() {
        musicControlBinder?.getCurrentTrack()?.apply {
            bar_music.max = duration
            tv_max_time.text = formatTime(duration)

            tv_singer_name.text = artistNameStr
            setTitle(name)

            Glide.with(App.context).asBitmap().load(album?.blurPicUrl).into(riv_music_img)
            ApiGenerator.getMoeApiService()
                .getLyric(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribeBy(onError = { e ->
                    e.printStackTrace()
                    ToastUtils.show("暂未获取数据:>")
                    lrc_detail.setLabel("获取歌词失败:(")
                }) { lyric ->
                    if (lyric.lrc != null) {
                        lrc_detail.loadLrc(lyric.lrc.lyric)
                    } else {
                        ToastUtils.show("暂时没有歌词呢:<")
                        lrc_detail.setLabel("暂时没有歌词呢:(")
                    }
                }

            iv_like.setImageResource(if (like) R.drawable.ic_like_on else R.drawable.ic_like_off)
            iv_star.setImageResource(if (star) R.drawable.ic_star_on else R.drawable.ic_star_off)

            selectMusicById(id).safeSubscribeBy(onError = { it.printStackTrace() }, onNext = {
                it ?: return@safeSubscribeBy
                like = it.like
                star = it.star
                category = it.category
                iv_like.setImageResource(if (it.like) R.drawable.ic_like_on else R.drawable.ic_like_off)
                iv_star.setImageResource(if (it.star) R.drawable.ic_star_on else R.drawable.ic_star_off)
            })

        }

        iv_play.setImageResource(
            if (musicControlBinder?.isPlaying() == true) {
                R.drawable.ic_play_running
            } else {
                R.drawable.ic_play_pause
            }
        )

        tv_cur_time.text = formatTime(musicControlBinder?.getCurrentProgress() ?: 0)
    }

    override fun onPlayStart() {
        iv_play.setImageResource(R.drawable.ic_play_running)
    }

    override fun onPlayPause() {
        iv_play.setImageResource(R.drawable.ic_play_pause)
    }

    override fun onPlayStop() {
        iv_play.setImageResource(R.drawable.ic_play_pause)
        onPlayProgressUpdate(0)
    }

    override fun onPlayProgressUpdate(progress: Int) {
        bar_music.progress = progress
        lrc_detail.updateTime(progress.toLong())
        tv_cur_time.text = formatTime(musicControlBinder?.getCurrentProgress() ?: 0)
    }

    override val resId: Int
        get() = R.layout.activity_music_detail


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        common_toolbar.init("")

        iv_play.setOnClickListener {
            if (musicControlBinder?.isPlaying() == true) {
                musicControlBinder?.pause()
            } else {
                musicControlBinder?.play()
            }
        }

        iv_move_back.setOnClickListener {
            musicControlBinder?.playPrev()
        }

        iv_move_forward.setOnClickListener {
            musicControlBinder?.playNext()
        }

        bar_music.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicControlBinder?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        lrc_detail.setOnPlayClickListener {
            musicControlBinder?.seekTo(it.toInt())
            return@setOnPlayClickListener true
        }

        iv_like.setOnClickListener { _ ->
            musicControlBinder?.getCurrentTrack()?.let {
                it.like = !it.like
                iv_like.setImageResource(if (it.like) R.drawable.ic_like_on else R.drawable.ic_like_off)
                insertMusic(music = it)
            }
        }

        iv_star.setOnClickListener { _ ->
            musicControlBinder?.getCurrentTrack()?.let { music ->
                if (music.star) {
                    music.star = !music.star
                    insertMusic(music)
                    iv_star.setImageResource(if (music.star) R.drawable.ic_star_on else R.drawable.ic_star_off)
                } else {
                    siv_detail.show(music.mood) {
                        music.star = !music.star
                        music.category = it
                        music.starDate = Date().time / 3600L * 3600L
                        insertMusic(music)
                        iv_star.setImageResource(if (music.star) R.drawable.ic_star_on else R.drawable.ic_star_off)
                    }
                }
            }
        }
    }

    private val formater = DecimalFormat("00")
    private fun formatTime(time: Int) = (time / 1000).let { "${it / 60}:${formater.format(it % 60)}" }

}
