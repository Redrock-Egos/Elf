package com.egos.elf.common.base

abstract class BaseNoNeedListenActivity : BaseActivity() {
    override fun onActivate() {}
    override fun onPlayStart() {}
    override fun onPlayPause() {}
    override fun onPlayStop() {}
    override fun onPlayProgressUpdate(progress: Int) {}
}