package com.example.myapplication.helper

import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.api.BannerHelper
import com.example.myapplication.myData.Story

/**
 * 辅助顶部无限轮播图
 * 封装了一个handler
 */
class BannerVpHelper(val mViewPager: ViewPager2, val mData: List<Story>) : BannerHelper {


    companion object {
        const val DELAY_MILLIS = 3000L // 轮播间隔时间，单位：毫秒
    }

    private lateinit var handler: Handler
    private var currentItem = 0
    private var isPaused = false

    private val runnable = object : Runnable {
        override fun run() {
            //定时任务
            if (!isPaused) {
                //这里的逻辑有点绕
                currentItem = mViewPager.currentItem
                currentItem++
                //其实就执行一次不是1到一个整体的长度
                if (currentItem % mData.size == 0) {
                    currentItem = 0
                }
                mViewPager.setCurrentItem(currentItem, true)
            }
            handler.postDelayed(this, DELAY_MILLIS)
        }

    }

    //开始轮播
    override fun startRun() {
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, DELAY_MILLIS)
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