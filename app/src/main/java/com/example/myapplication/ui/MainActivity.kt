package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.adapter.RvNewsAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.util.DateUtil
import com.example.myapplication.viewmodel.MyViewModel
import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    //获得当前时间
    @RequiresApi(Build.VERSION_CODES.O)
    private var currentDate: LocalDate = LocalDate.now()
    private val myViewModel by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }

    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        /**
         * 轻量储存数据
         */
        // 获取 SharedPreferences 对象
        val sharedPreferences = getSharedPreferences("MY_SHARED_PREFERENCES", Context.MODE_PRIVATE)
        // 将 LiveData 对象的值以 JSON 字符串的形式保存到 SharedPreferences 中
        val editor = sharedPreferences.edit()

        /**
         * 下面是rnTopStorySendQuest()，用于轮播图
         */
        val viewPager: ViewPager2 = mBinding.bannerViewPager
        val adapter = NewsAdapter()
        myViewModel.newsTopData.observe(this) { news ->
            adapter.submitList(news)
            Log.d("96369", "(MainActivity.kt:69)-->> $news");
        }
        viewPager.adapter = adapter
        editor.putString("LIVE_DATA_KEY", Gson().toJson(myViewModel.newsRecentData.value))
        editor.apply()
        /**
         * 下面是rnRecentStoryQuest，用于展示recyclerview
         */
        val mdata = ArrayList<String>()
        myViewModel.newsRecentData.observe(this) { list ->
            for (it in list) {
                mdata.add(it.url)
            }
            Log.d("854785", "(MainActivity.kt:114)-->> $mdata")
        }
        val rvViewPager: RecyclerView = mBinding.recyclerView
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        val rvAdapter = RvNewsAdapter()
        myViewModel.newsRecentData.observe(this) { it ->
            rvAdapter.submitList(it)
        }
        rvViewPager.adapter = rvAdapter
        /**
         * 下面是向上刷新
         */
        mBinding.swipeRefresh.apply {
            setColorSchemeColors(R.color.black)
            setOnRefreshListener {
                adapter.notifyDataSetChanged()
                rvAdapter.notifyDataSetChanged()
                mBinding.swipeRefresh.isRefreshing = false
            }
        }
        /**
         * 向下刷新
         */
        mBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("NotifyDataSetChanged")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount - 1 == lastVisibleItemPosition && !recyclerView.canScrollVertically(
                        1
                    )
                ) {
                    // 将日期减去一天
                    currentDate = currentDate.minusDays(1)
                    // 将日期格式化为字符串
                    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                    val m = currentDate.format(formatter)
                    Log.d("mDtdd", "(MainActivity.kt:94)-->> $m")
                    myViewModel.rvBeforeStoryQuest(m)
                    editor.putString(
                        "LIVE_DATA_KEY",
                        Gson().toJson(myViewModel.newsRecentData.value)
                    )
                    editor.apply()
                    rvAdapter.notifyDataSetChanged()
                }
            }
        })
        /**
        下面是设置了轮播的时间间隔：3000ms（3秒）
        原理：
         */
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
        viewPager.postDelayed({
            viewPager.currentItem = (viewPager.currentItem + 1) % adapter.itemCount
        }, 3000)

        // 将日期设置为Toolbar标题
        mBinding.toolBarTime.text = DateUtil.getDateTitle()
    }

}









