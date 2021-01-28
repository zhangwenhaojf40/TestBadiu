package com.example.myapplication

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidquery.AQuery
import com.baidu.mobad.feeds.RequestParameters
import com.baidu.mobads.AppActivity
import com.baidu.mobads.MobadsPermissionSettings
import com.baidu.mobads.nativecpu.CPUAdRequest
import com.baidu.mobads.nativecpu.CpuLpFontSize
import com.baidu.mobads.nativecpu.IBasicCPUData
import com.baidu.mobads.nativecpu.NativeCPUManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_award_news_native.*
import java.util.*
import javax.net.ssl.KeyManager

/**
* author：yang
* created on：2020/11/10
* description: 内容咨询
*/
class AwardNewsNativeFragment11 : BaseFragment() {
    private lateinit var mADAdapter: ADAdapter
    private var mCurPageIndex = 0
    private var mChannelId: Int = 1022
    private lateinit var mCpuManager: NativeCPUManager
    private val mTotalAdList = ArrayList<IBasicCPUData>()
    companion object {
        const val PARAMS_ID = "params_id"
        fun newInstance(id: Int): AwardNewsNativeFragment11{
            return AwardNewsNativeFragment11().apply {
                arguments = Bundle().apply {
                    putInt(PARAMS_ID, id)
                }
            }
        }
    }

    override fun initListen() {
    }

    override fun getLayoutRes(): Int =R.layout.fragment_award_news_native

    override fun initView() {

        rvList.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            mADAdapter = ADAdapter().apply {
                setOnItemClickListener { _, view, position ->
                    //处理广告&内容的点击事件: 影响计费
                    data[position].handleClick(view)
                }
                loadMoreModule?.run {
                    isEnableLoadMore = true
                    preLoadNumber = 4
                    setOnLoadMoreListener { loadAd() }
                }
            }
            adapter = mADAdapter
        }
    }

    override fun initData() {
        mChannelId = arguments!!.getInt(PARAMS_ID, 1022)
        initBaiDu()
        loadAd()
    }

    private fun initBaiDu() {
        MobadsPermissionSettings.setPermissionReadDeviceID(true)
        MobadsPermissionSettings.setPermissionAppList(true)
        AppActivity.setActionBarColorTheme(AppActivity.ActionBarColorTheme.ACTION_BAR_WHITE_THEME)
        mCpuManager = NativeCPUManager(activity, "c39aa97a", LoadListener()).apply {
            setLpFontSize(CpuLpFontSize.REGULAR)
            setLpDarkMode(false)
            setRequestParameter(CPUAdRequest.Builder().apply {
                setDownloadAppConfirmPolicy(RequestParameters.DOWNLOAD_APP_CONFIRM_ONLY_MOBILE)
                // 少部分机型出现无法获取手机设备信息问题，媒体可以通过设置CustomUserId来代替，格式要求16位的数字和字母（不区分大小写）
                setCustomUserId("12345678900")
            }.build())
            setRequestTimeoutMillis(10 * 1000) // 默认5s请求超时
        }
    }

    fun loadAd() {
        mCpuManager.loadAd(++mCurPageIndex, mChannelId, true)
    }

    inner class LoadListener: NativeCPUManager.CPUAdListener{
        override fun onAdLoaded(p0: MutableList<IBasicCPUData>?) {
            mADAdapter.loadMoreModule?.loadMoreComplete()

            p0?.run {
                mTotalAdList.addAll(p0)
                mADAdapter.notifyDataSetChanged()
            }
        }
        override fun onAdError(p0: String?, p1: Int) {
            mADAdapter.loadMoreModule?.loadMoreComplete()
            p0?.let { toast(it) }
        }
        override fun onNoAd(p0: String?, p1: Int) {
            mADAdapter.loadMoreModule?.loadMoreComplete()
        }
        override fun onAdClick() {
            println("onAdclick")
        }
        override fun onVideoDownloadSuccess() {}
        override fun onVideoDownloadFailed() {
            println("onVideoDownloadFailed")
        }
        override fun onAdStatusChanged(p0: String?) {
            println("onAdStatusChanged")
        }
    }

    inner class ADAdapter: BaseQuickAdapter<IBasicCPUData, BaseViewHolder>(R.layout.item_award_news_native, mTotalAdList),
        LoadMoreModule {
        private val aq: AQuery = AQuery(activity)
        override fun convert(helper: BaseViewHolder, item: IBasicCPUData?) {
            println("type=========${item?.type}")
            val parent = helper.getView<ViewGroup>(R.id.native_outer_view)
            var cpuView = if(parent.childCount != 0){
                parent.getChildAt(0) as NativeCPUView
            }else{
                NativeCPUView(activity).apply { parent.addView(this) }
            }
            cpuView.setItemData(item, aq)
            //广告展现：影响计费
            item?.onImpression(parent)
        }
    }
}


