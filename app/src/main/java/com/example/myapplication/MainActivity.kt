package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.topNewsAdapter.NewsAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.myData.Story
import com.example.myapplication.newsAdapter.RvNewsAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {



    //懒加载注入viewmodel
    private val myViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }

    //懒加载注入databinding
    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        /**
         * 下面是rnTopStorySendQuest()，用于轮播图
         */
        myViewModel.rnTopStorySendQuest()
        val viewPager:ViewPager2 = mBinding.bannerViewPager
        val adapter = NewsAdapter()
        myViewModel.newsTopData.observe(this) { news ->
            adapter.submitList(news)
        }
        viewPager.adapter = adapter



        /**
         * 下面是rnRecentStoryQuest，用于展示recyclerview
         */
        myViewModel.rnRecentStoryQuest()

        val rv_viewPager:RecyclerView = mBinding.recyclerView
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        val rv_adapter =RvNewsAdapter()
        myViewModel.newsRecentData.observe(this) { it ->
            rv_adapter.submitList(it)
        }
        rv_viewPager.adapter=rv_adapter
        /**
         * 下面是向上刷新
         */
        //todo:初始化不够完整
        mBinding.swipeRefresh.setColorSchemeColors(R.color.black)//设置进度条颜色
        mBinding.swipeRefresh.setOnRefreshListener {
            myViewModel.rnTopStorySendQuest()
            val viewPager:ViewPager2 = mBinding.bannerViewPager
            val adapter = NewsAdapter()
            myViewModel.newsTopData.observe(this) { news ->
                adapter.submitList(news)
                viewPager.adapter = adapter
            }
            mBinding.swipeRefresh.isRefreshing = false

        }
        /**
         * 向下刷新
         */
        mBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount - 1 == lastVisibleItemPosition) {
                    // 在这里触发新的网络请求
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

//      TODO:可以对时间进行一个选择，时间不同的时候，title设置为不同的值
        setSupportActionBar(mBinding.toolBar)
        val currentDate = Date()//获取时间
        val dateFormat = SimpleDateFormat("M月dd日", Locale.getDefault())
        val dateString = dateFormat.format(currentDate)
        Log.d("555", "(MainActivity.kt:20)-->> " + dateString);
        // 将日期设置为Toolbar标题
        mBinding.toolBar.title = "$dateString         新闻简阅"
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_user -> {
                Toast.makeText(this, "您点开了用户", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }
        }
        return true
    }
}








