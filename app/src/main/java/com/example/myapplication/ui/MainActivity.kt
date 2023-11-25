package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.BannerAdapter
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.api.BannerHelper
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.helper.BannerVpHelper
import com.example.myapplication.myData.Story
import com.example.myapplication.util.DateUtil
import com.example.myapplication.viewmodel.MyViewModel
import com.google.gson.Gson
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.BezierRadarHeader

class MainActivity : AppCompatActivity() {

    private lateinit var mTopNewsList: MutableList<Story>
    private lateinit var mNewsList: MutableList<Story?>
    private lateinit var mAllList: MutableList<Story?>
    private val rvAdapter: NewsAdapter by lazy { NewsAdapter() }


    private val myViewModel by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }

    private val bannerVpHelper by lazy { BannerVpHelper() }
    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var handler: Handler
    private val runnable = object : Runnable {

        override fun run() {
            handler.postDelayed(this, REFRESH_TIME)
        }
    }

    companion object {
        private const val REFRESH_TIME = 5000L
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        iniView()
        iniTrans()
        iniRefresh()
//        iniHandler()

    }

    private fun iniHandler() {
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, REFRESH_TIME)
    }


    private fun iniTrans() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniView() {
        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val gson = Gson()
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)

        //因为设置了网络请求的限制，所以第二个观察一点晚于第一个
        myViewModel.newsTopData.observe(this) { storyList ->
            mTopNewsList = storyList as MutableList<Story>
            rvAdapter.onInitBanner {
                it.submitBannerList(mTopNewsList)
                bannerVpHelper.initBanner(it)
            }
        }
        myViewModel.newsRecentData.observe(this) {
            mNewsList = (it as MutableList<Story>).toMutableList()
            mNewsList.add(0, null)
            rvAdapter.submitList(mNewsList)
            mAllList = (mTopNewsList + it).toMutableList()
            editor.putString("all_list_key", gson.toJson(mAllList))
            editor.apply()

            mBinding.toolBarTime.text = DateUtil.getDateTitle()
        }

        bannerVpHelper.viewPager2Adapter.setOnItemClick {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("newsId", mTopNewsList[it].id)
            intent.putExtra("topNewsUrl", mTopNewsList[it].url)
            startActivity(intent)
        }
        mBinding.recyclerView.adapter = rvAdapter.apply {
            setOnItemClick {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                //设置标志符
                intent.putExtra("topNewsUrl", mNewsList[it]?.url)
                intent.putExtra("newsId", mNewsList[it]?.id)
                startActivity(intent)

            }
        }
    }

    //处理刷新和加载
    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniRefresh() {
        mBinding.swipeRefresh.apply {
            setRefreshHeader(BezierRadarHeader(this@MainActivity))
            setRefreshFooter(BallPulseFooter(this@MainActivity))
            setOnRefreshListener {
                myViewModel.apply {
                    receiveTopNews()
                }
                finishRefresh(500)
            }
            setOnLoadMoreListener {
                myViewModel.apply {
                    myViewModel.rvBeforeStoryQuest(DateUtil.getDataBefore())
                }
                finishLoadMore(500)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //销毁handler
        bannerVpHelper.destroyRun()
//        handler.removeCallbacks(runnable)
    }
}









