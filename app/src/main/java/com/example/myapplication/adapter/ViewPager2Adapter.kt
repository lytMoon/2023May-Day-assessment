package com.example.myapplication.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/25
 * version: 1.0
 */


open class ViewPager2Adapter(fa: FragmentActivity, private val list: MutableList<Fragment>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

}
