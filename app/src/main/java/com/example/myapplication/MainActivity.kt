package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {


    val imageUrlList = mutableListOf<String>()

    //懒加载注入viewmodel
    private val myViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }

    //懒加载注入databinding
    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        myViewModel.rnTopStorySendQuest()
        val viewPager:ViewPager2 = mBinding.bannerViewPager
        val adapter = NewsAdapter()
        myViewModel.newsTopData.observe(this) { news ->
            adapter.submitList(news)
        }
        viewPager.adapter = adapter
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
            }
        }
        return true
    }
}








