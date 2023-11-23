package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.RvNewsAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.helper.BannerVpHelper
import com.example.myapplication.myData.Story
import com.example.myapplication.myData.TopStory
import com.example.myapplication.util.DateUtil
import com.example.myapplication.viewmodel.MyViewModel
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.BezierRadarHeader

class MainActivity : AppCompatActivity() {

    private lateinit var mTopNewsList: MutableList<TopStory>
    private lateinit var mNewsList: MutableList<Story?>
    private lateinit var rvAdapter: RvNewsAdapter


    private val myViewModel by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }


    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        iniView()
        iniTrans()
        iniRefresh()
    }


    private fun iniTrans() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniView() {
        // 将日期设置为Toolbar标题
        mBinding.toolBarTime.text = DateUtil.getDateTitle()
        myViewModel.newsTopData.observe(this) { it ->
            mTopNewsList = it as MutableList<TopStory>
            rvAdapter = RvNewsAdapter(mTopNewsList)
            mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
            myViewModel.newsRecentData.observe(this) {
                mNewsList = (it as MutableList<Story>).toMutableList()
                mNewsList.add(0, null)
                rvAdapter.submitList(mNewsList)
            }
            mBinding.recyclerView.adapter = rvAdapter
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
                    receiveNewsData()
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


    /**
     * TODO:这里有一点没弄明白
     *
     *      rvAdapter.submitList(it)
     *             Log.d("999999", "MainActivity.kt:------>${it.size}")
     *             mNewsList = it as MutableList<Story?>
     *             mNewsList.add(0, null)
     *             Log.d("111111", "MainActivity.kt:------>${it.size}")
     *             这种写法两个it是不一样的值，
     *             但是observe函数就调用了一次
     *
     *             按道理来说adapter获取的应该是size为5的（第一个元素不是空），但是实际是获取的是第二个size为6的集合，这是为什么？
     *
     *             但是把第一种写法换成
     *             mNewsList = (it as MutableList<Story>).toMutableList()
     *             就一样了，这时为什么？
     *             初步怀疑    mNewsList = it as MutableList<Story?>这种写法的强制类型转换 没有执行as后面的，把it地址给了mNewsList，由于it变化了
     *             经过了一些手段，重新或者说 rvAdapter.submitList(it)传入的it为最新的6 而不是5，而不是observe无限循环（可以理解最后执行的它）
     *
     */

    override fun onDestroy() {
        super.onDestroy()
        //销毁handler
        rvAdapter.bannerVpHelper.destroyRun()
    }
}









