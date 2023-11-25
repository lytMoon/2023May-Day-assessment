package com.example.myapplication.helper

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapter.BannerAdapter
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.api.BannerHelper
import com.example.myapplication.myData.Story
import kotlin.properties.Delegates

/**
 * 辅助顶部无限轮播图
 * 使用BannerVpHelper 统一管理轮播图
 */
class BannerVpHelper() : BannerHelper {

    private lateinit var mViewPager: ViewPager2
    private lateinit var mData: List<Story>

    companion object {
        const val DELAY_MILLIS = 7000L // 轮播间隔时间，单位：毫秒
        const val DETECTIVE_MILLIS = 500L // 检测短时间内滑动的时间间隔，单位：毫秒
    }

    private lateinit var handler: Handler
    private var currentItem = 0

    //记录最后的一次banner滑动状态
    private var lastState by Delegates.notNull<Int>()

    //短时间内的滑动次数模拟速度
    private var draggingSpeed = 0
    private var isLongTimeDragging = false
    private var isUserQuicklyDragging = false
    val viewPager2Adapter: BannerAdapter by lazy { BannerAdapter() }
    private lateinit var newsAdapter: NewsAdapter
    private val runnable = object : Runnable {
        //指定时间执行轮播
        override fun run() {
            if (!isLongTimeDragging && !isUserQuicklyDragging) {
                currentItem++
                isLongTimeDragging = false
                mViewPager.setCurrentItem(currentItem, true)
            }
            handler.postDelayed(this, DELAY_MILLIS)
        }
    }
    private val runnable1 = object : Runnable {
        //检测短时间内滑动次数
        override fun run() {
            /**
             * 只有在这种情况下才会更新状态
             */
            if (!isLongTimeDragging && !isUserQuicklyDragging) {
                newsAdapter.isScrolling = false
                viewPager2Adapter.addPageChange(false)
            } else {
                newsAdapter.isScrolling = true
                viewPager2Adapter.addPageChange(true)
            }


            if (isLongTimeDragging) {
                //更新状态
            } else {
                //第一次操作才有缓冲时间，第二次没有
                if (draggingSpeed > 30) {
                    draggingSpeed = 30
                }
                //速度过快的时候会走这里
                if (draggingSpeed in 21..30) {
                    //更新状态
                    isUserQuicklyDragging = true
                    //速度快到突然停下来的特殊情况
                    if (lastState == ViewPager2.SCROLL_STATE_DRAGGING) {
                        isLongTimeDragging = true
                        isUserQuicklyDragging = false
                    }
                }
                //离手的情况
                if (draggingSpeed <= 0) {
                    draggingSpeed = 0
                    isUserQuicklyDragging = false
                }
                //波动一次，相当于轮播图暂停了30/2  = 15 15*handler周期  等于7.5秒
                draggingSpeed -= 2
            }
            handler.postDelayed(this, DETECTIVE_MILLIS)
        }
    }

    override fun initBanner(rv: NewsAdapter) {
        newsAdapter = rv
        //赋值视图
        mViewPager = rv.mViewPager
        //获取集合
        mData = rv.bannerNewsList
        //展示轮播图
        viewPager2Adapter.submitList(rv.bannerNewsList)
        mViewPager.adapter = viewPager2Adapter
        //开始轮播
        startRun()
        //检测滑动事件
        onPageChange()
    }

    //检测手指的滑动事件
    private fun onPageChange() {
        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                lastState = state
                Log.d("8588585489859", "BannerVpHelper.kt:------>${lastState}")
                when (state) {
                    //拖拽事件
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        //标记短时间内滑动了多少次 ,为了方便统计，每次变化多一点
                        draggingSpeed += 35
                        //这种情况才能标记我们第一次的滑动
                        if (!isUserQuicklyDragging) {
                            isLongTimeDragging = true
                        }
                    }

                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        if (!isUserQuicklyDragging) {
                            isLongTimeDragging = false
                        }
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
        //这里会开启两个任务
        handler.postDelayed(runnable, DELAY_MILLIS)
        handler.postDelayed(runnable1, DETECTIVE_MILLIS)
    }

    fun changeCurrentPage(c: Int) {
        currentItem = c
    }


    //结束轮播
    override fun destroyRun() {
        handler.removeCallbacks(runnable, runnable1)//释放我们的handler
    }


}