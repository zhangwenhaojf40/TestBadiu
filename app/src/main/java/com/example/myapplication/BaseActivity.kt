package com.example.myapplication

import android.app.ActivityManager
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

val todo = "todo"

/**
 *  Create by Zwh on 2020/10/13
 *  DESC:
 */
abstract class BaseActivity : AppCompatActivity() {

    private var loadDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //默认竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setStatusBar()
        setContentView(getLayoutRes())
        setTitle()
        initView()
        initListen()
        initData()
        setBack()
    }



    abstract fun getLayoutRes(): Int

    abstract fun initTitle(): String?

    private fun setTitle() {

    }

    abstract fun initView()

    abstract fun initListen()

    abstract fun initData()

    private fun setBack() {

    }

    /**
     * 设置状态栏样式，默认为白色(非透明)
     * 该方法是open方法，如果子类需要改变状态栏样式，请重写该方法
     * 如果子类需要透明状态栏，则重写该方法后调用 {@link transparentStatusBar}
     * 如果是Activity中的Fragment状态栏样式不统一，请在Fragment中单独设置statusBar
     * 同时Activity重写并留空该方法
     */
    protected open fun setStatusBar() {
    }



    /**
     * 显示Toast提示
     */
    fun toast(toast: String?) {
    }

    fun toast(toast: Int) {
    }

    /**
     * 联网加载框隐藏
     * */
    fun hideLoading() {
        try {
            if (isFinishing) {
                return
            }
            loadDialog?.cancel()
            loadDialog?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}