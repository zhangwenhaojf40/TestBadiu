package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.baidu.mobads.MobadsPermissionSettings
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_award_news_native.*

/**
* author：yang
* created on：2020/11/9
* description: 新闻咨询：本地SDK内容
*/
class AwardNewsNativeActivity : BaseActivity() {
    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, AwardNewsNativeActivity::class.java))
        }
    }
    //在详情页
    private var mIsDetail = false

    //开启悬浮窗请求码
    private val REQUEST_CODE_FLOAT = 1001
    //悬浮窗提示
    private var mDialogFloat: Dialog? = null
    //频道列表
    private val mTitlesMap = mapOf(
            "推荐" to 1022, "娱乐" to 1001, "视频" to 1057, "热讯" to 1081, "健康" to 1043, "军事" to 1012,
            "母婴" to 1042, "生活" to 1035, "游戏" to 1040, "汽车" to 1007, "财经" to 1006, "科技" to 1013,
            "热点" to 1021, "图集" to 1068, "搞笑" to 1025, "体育" to 1002, "时尚" to 1009, "女人" to 1034,
            "本地" to 1080, "萌萌哒" to 1065, "看点" to 1047, "动漫" to 1055, "小品" to 1062, "文化" to 1036,
            "手机" to 1005, "房产" to 1008, "音乐" to 1058, "搞笑" to 1059, "影视" to 1060, "游戏" to 1067,
            "生活" to 1066, "观天下" to 1064, "娱乐" to 1061, "社会" to 1063)



    override fun initData() {
        MobadsPermissionSettings.setPermissionReadDeviceID(true)
        MobadsPermissionSettings.setPermissionAppList(true)

    }

    override fun getLayoutRes(): Int = R.layout.activity_award_news_native

    override fun initTitle(): String? =""

    override fun initView() {
        vpContent.run {
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
                override fun onPageSelected(i: Int) {
                    if (tabLayout.currentTab != i) {
                        tabLayout.currentTab = i
                    }
                }
                override fun onPageScrollStateChanged(i: Int) {}
            })
            adapter = PageAdapter()
        }

        tabLayout.run {

            setViewPager(vpContent,  mTitlesMap.keys.toTypedArray())
            setOnTabSelectListener(object : OnTabSelectListener {
                override fun onTabSelect(position: Int) {
                    if (vpContent.currentItem != position) {
                        vpContent.setCurrentItem(position, true)
                    }
                }
                override fun onTabReselect(position: Int) {}
            })
        }

        vpContent.setCurrentItem(0, true)
    }

    override fun initListen() {
    }

    inner class PageAdapter: FragmentPagerAdapter(supportFragmentManager){
        override fun getCount(): Int = mTitlesMap.size
        override fun getItem(position: Int): Fragment{
            val channelId = mTitlesMap.toList()[position].second
            return AwardNewsNativeFragment11.newInstance(channelId)
        }
    }
}