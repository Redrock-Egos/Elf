package com.egos.elf.common.base

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.egos.elf.R
import com.egos.elf.common.service.MusicService
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.include_toolbar_backable.*

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity(), MusicService.MusicPlayStatusListener {

    protected var musicControlBinder: MusicService.MusicControlBinder? = null

    protected open val needSlide: Boolean = true

    @get:LayoutRes
    abstract val resId: Int

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            musicControlBinder = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            musicControlBinder = service as MusicService.MusicControlBinder
            musicControlBinder?.updateTopListener(this@BaseActivity)
            onServiceConnected()
        }
    }

    protected fun Toolbar.init(
        title: String,
        @DrawableRes icon: Int = R.drawable.ic_back,
        listener: View.OnClickListener? = View.OnClickListener { finish() }
    ) {
        toolbar_title.text = title
        setSupportActionBar(this)
        if (listener == null) {
            navigationIcon = null
        } else {
            setNavigationIcon(icon)
            setNavigationOnClickListener(listener)
        }
    }

    protected val BaseActivity.common_toolbar get() = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resId)

        if (needSlide) {
            Slidr.attach(this)
        }

        val decorView = window.decorView
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun onStart() {
        super.onStart()
        musicControlBinder?.updateTopListener(this) ?: bindService()
    }

    override fun onDestroy() {
        musicControlBinder?.updateTopListener(null)
        unbindService()
        super.onDestroy()
    }

    private fun bindService() {
        startService(Intent(this, MusicService::class.java))
        bindService(Intent(this, MusicService::class.java), serviceConnection, BIND_AUTO_CREATE)
    }

    private fun unbindService() {
        unbindService(serviceConnection)
    }

    protected open fun onServiceConnected() {}

}