package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapter.ViewPager2Adapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragment.HomeFragment
import com.example.myapplication.fragment.PlayGroundFragment
import com.example.myapplication.fragment.UserFragment

class MainActivity : AppCompatActivity() {
    //设置fragment集合，用于vp2进行页面跳转绑定
    val fragmentList: MutableList<Fragment> = ArrayList()
    //懒加载注入databinding
    private val mBinding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        //      下面进行fragment和viewpager的绑定
        //去除自带的选中颜色,去除后文字和图片选择效果就是跟我们自定义的效果一样
        mBinding.navView.itemIconTintList = null

        fragmentList.add(HomeFragment())
        fragmentList.add(PlayGroundFragment())
        fragmentList.add(UserFragment())//添加3个fragment
        mBinding.viewPager2.adapter = ViewPager2Adapter(this, fragmentList)
        //当viewpage2页面切换时nav导航图标也跟着切换
        mBinding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mBinding.navView.menu.getItem(position).isChecked = true
            }
        })

//当nav导航点击切换时，viewpager2也跟着切换页面
        //mBinding 顺便调用了viewpager2
        mBinding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_home -> {
                    mBinding.viewPager2.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_nav_playground -> {
                    mBinding.viewPager2.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_nav_user -> {
                    mBinding.viewPager2.currentItem = 2
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }




}





