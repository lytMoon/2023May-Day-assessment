package com.example.myapplication.helper

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapter.BannerAdapter
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.api.BannerHelper
import com.example.myapplication.myData.Story

/**
 * 辅助顶部无限轮播图
 * 封装了一个handler
 */
class BannerVpHelper() : BannerHelper {

    lateinit var mViewPager: ViewPager2
    lateinit var mData: List<Story>


    companion object {
        const val DELAY_MILLIS = 3000L // 轮播间隔时间，单位：毫秒
    }

    private lateinit var handler: Handler
    private var currentItem = 0
    private var isUserScrolling = false
    val viewPager2Adapter: BannerAdapter = BannerAdapter()

    private var isScrolling = false

    private var isPaused = false
    private val runnable = object : Runnable {

        override fun run() {
            //定时任务
            if (!isPaused) {
                //这里的逻辑有点绕
                currentItem++
                mViewPager.setCurrentItem(currentItem, true)
            }
            handler.postDelayed(this, DELAY_MILLIS)
        }

    }


    //初始化Banner的Adapter


    fun initBanner(rv: NewsAdapter) {
        mViewPager = rv.mViewPager
        mData = rv.bannerNewsList
        startRun()
        viewPager2Adapter.submitList(rv.bannerNewsList)
        mViewPager.adapter = viewPager2Adapter
        onPageChange()
    }

    //检测手指的滑动事件
    private fun onPageChange() {
        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    //拖拽事件
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        Log.d("484848", "NewsAdapter.kt:------")
                        pauseLoop()
                        isScrolling = true
                        viewPager2Adapter.addPageChange(true)

                    }
                    //手指松开的事件
                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        resumeLoop()
                        isScrolling = false
                        viewPager2Adapter.addPageChange(false)
                    }

                    else -> {
                    }
                }
            }

            /**
             *因为默认vp从0开始所以最早会触发这个回调，我么那就让他迅速的转移到较大的位置
             * 这个回调执行的逻辑只会执行一次
             */
            var once = false
            override fun onPageSelected(position: Int) {

                Log.d("5858958948548945", "NewsAdapter.kt:------>${position}")
                if (!once) {
                    once = true
                    mViewPager.setCurrentItem(mData.size * 100, false)
                }
                /**
                 * 这里为了防止0过快的把current覆盖，所以做了一个判断，必须让position不能为0的时候才进行值的传递。及时更新
                 */
                if (position != 0) {
                    changeCurrentPage(position)
                }
            }
        })
    }


    //开始轮播
    override fun startRun() {
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, DELAY_MILLIS)
    }


    fun changeCurrentPage(c: Int) {
        currentItem = c
    }


    //结束轮播
    override fun destroyRun() {
        handler.removeCallbacks(runnable)//释放我们的handler
    }

    // 暂停轮播
    override fun pauseLoop() {
        isPaused = true
    }

    // 恢复轮播
    override fun resumeLoop() {
        isPaused = false
    }


}