package com.egos.elf.common.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.egos.elf.App
import com.egos.elf.common.bean.moe.Music
import com.egos.elf.common.utils.ElfMusicPlayer

class MusicService : Service(), ElfMusicPlayer.MusicPlayerStatusListener {

    private var mMusicIndicator = 0
    private var topListener: MusicPlayStatusListener? = null

    private lateinit var mPlayer: ElfMusicPlayer
    private lateinit var mMusicControlBinder: IBinder

    override fun onCreate() {
        super.onCreate()
        mMusicControlBinder = MusicControlBinder()
        mPlayer = ElfMusicPlayer()
        mPlayer.mStatusListener = this
        App.playListManager.changeListener = {
            mMusicIndicator = -1
            playNextMusic()
        }
    }

    override fun onDestroy() {
        mPlayer.release()
        super.onDestroy()
    }

    override fun onPlayerStart() {
        topListener?.onPlayStart()
    }

    override fun onPlayerPause() {
        topListener?.onPlayPause()
    }

    override fun onPlayerStop() {
        topListener?.onPlayStop()
    }

    override fun onPlayerComplete() {
        playNextMusic()
    }

    override fun onPlayerProgressUpdate(progress: Int) {
        topListener?.onPlayProgressUpdate(progress)
    }

    override fun onBind(intent: Intent) = mMusicControlBinder

    private fun playMusic(track: Music) {
        mPlayer.playMusic(getMusicPath(track.id))
        topListener?.onActivate()
    }

    private fun getMusicPath(id: Long) =
        App.getProxy().getProxyUrl("http://music.163.com/song/media/outer/url?id=$id.mp3")

    private fun playNextMusic() {
        val currentList = App.playListManager.getCurrentPlayList()
        currentList ?: return
        if (++mMusicIndicator == currentList.tracks.size) {
            mMusicIndicator = 0
        }
        currentList.tracks ?: return
        playMusic(currentList.tracks[mMusicIndicator])
    }

    private fun playPrevMusic() {
        val currentList = App.playListManager.getCurrentPlayList()
        currentList ?: return
        if (--mMusicIndicator < 0) {
            mMusicIndicator = currentList.tracks.size - 1
        }
        currentList.tracks ?: return
        playMusic(currentList.tracks[mMusicIndicator])
    }

    inner class MusicControlBinder : Binder() {

        fun updateTopListener(listener: MusicPlayStatusListener?) {
            topListener = listener
            topListener?.onActivate()
        }

        fun getCurrentTrack() = App.playListManager.getCurrentPlayList()?.tracks?.get(mMusicIndicator)

        fun play() {
            if (mPlayer.isPause()) {
                mPlayer.start()
            } else {
                playMusic(getCurrentTrack() ?: return)
            }
        }

        fun pause() = mPlayer.pause()

        fun stop() = mPlayer.stop()

        fun seekTo(pos: Int) {
            mPlayer.pause()
            mPlayer.seekTo(pos)
            mPlayer.start()
        }

        fun playNext() = playNextMusic()

        fun playPrev() = playPrevMusic()

        fun getCurrentMusicIndicator() = mMusicIndicator

        fun getCurrentProgress() = mPlayer.currentPosition

        fun getDuration() = mPlayer.duration

        fun getStatus() = mPlayer.mPlayStatus

        fun isPlaying() = mPlayer.isPlaying

        fun setPlayPos(pos: Int) {
            val currentList = App.playListManager.getCurrentPlayList()
            currentList ?: return
            if (pos >= currentList.tracks.size || pos < 0) {
                return
            }
            mMusicIndicator = pos
            playMusic(getCurrentTrack() ?: return)
        }
    }

    interface MusicPlayStatusListener {
        fun onActivate()
        fun onPlayStart()
        fun onPlayPause()
        fun onPlayStop()
        fun onPlayProgressUpdate(progress: Int)
    }
}
