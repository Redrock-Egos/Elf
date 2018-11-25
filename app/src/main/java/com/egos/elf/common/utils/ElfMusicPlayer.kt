package com.egos.elf.common.utils

import android.media.MediaPlayer
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class ElfMusicPlayer : MediaPlayer() {
    companion object {
        const val STATUS_PLAYING = 0
        const val STATUS_PAUSE = 1
        const val STATUS_STOP = 2
    }

    var mPlayStatus = STATUS_STOP
        private set

    var mStatusListener: MusicPlayerStatusListener? = null

    init {
        setOnCompletionListener { mStatusListener?.onPlayerComplete() }
    }

    private val updateProgressRunnable = object : Runnable {
        override fun run() {
            if (isPlaying && mStatusListener != null) {
                mStatusListener?.onPlayerProgressUpdate(currentPosition)
                AndroidSchedulers.mainThread().scheduleDirect(this, 500, TimeUnit.MILLISECONDS)
            }
        }
    }

    override fun start() {
        super.start()
        mPlayStatus = STATUS_PLAYING
        mStatusListener?.onPlayerStart()
        AndroidSchedulers.mainThread().scheduleDirect(updateProgressRunnable)
    }

    override fun pause() {
        super.pause()
        mPlayStatus = STATUS_PAUSE
        mStatusListener?.onPlayerPause()
    }

    override fun stop() {
        super.stop()
        mPlayStatus = STATUS_STOP
        mStatusListener?.onPlayerStop()
    }

    override fun reset() {
        super.reset()
        mPlayStatus = STATUS_STOP
    }

    fun playMusic(path: String) {
        reset()
        setDataSource(path)
        prepare()
        seekTo(0)
        start()
    }

    fun isPause() = mPlayStatus == STATUS_PAUSE

    interface MusicPlayerStatusListener {
        fun onPlayerStart()
        fun onPlayerPause()
        fun onPlayerStop()
        fun onPlayerComplete()
        fun onPlayerProgressUpdate(progress: Int)
    }
}