package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *  Create by Zwh on 2020/10/13
 *  DESC:
 */
abstract class BaseFragment : Fragment() {
    var mView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(getLayoutRes(), container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBar()
        initView()
        initListen()
        initData()
    }

    abstract fun initListen()

    open fun setStatusBar(){}

    abstract fun getLayoutRes(): Int

    abstract fun initView()

    abstract fun initData()

    protected fun whiteStatusBar() {

    }

    protected fun selectStatusBar(color : String) {

    }
    protected fun transparentStatusBar() {

    }

    fun getStatusBarHeight () : Int {
        return getStatusBarHeight()
    }
    /**
     * 显示Toast提示
     */
    fun toast(toast: String?) {
    }

    fun toast(toast: Int) {
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
    }
}