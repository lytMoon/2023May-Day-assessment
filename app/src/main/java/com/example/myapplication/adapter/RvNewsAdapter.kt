package com.example.myapplication.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.ui.DetailActivity
import com.example.myapplication.R
import com.example.myapplication.helper.BannerVpHelper
import com.example.myapplication.myData.Story
import com.example.myapplication.myData.TopStory

/**
 * description ：展示新闻列表
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:49
 * version: 1.0
 */
class RvNewsAdapter(private val topNewsList: List<TopStory>) :
    ListAdapter<Story, ViewHolder>(object : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.title == newItem.title
        }
    }) {


    lateinit var bannerVpHelper: BannerVpHelper
    private lateinit var mViewPager: ViewPager2


    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            //这里相当于当item为0的时候为轮播图的viewHolder做了个标记
            0 -> 0
            else -> 1
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        when (viewType) {
            //轮播图
            0 -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_top_rv, parent, false)
                mViewPager = view.findViewById(R.id.banner_view_pager)
                val viewPager2Adapter = TopNewsAdapter()
                viewPager2Adapter.submitList(topNewsList)
                mViewPager.adapter = viewPager2Adapter
                bannerVpHelper = BannerVpHelper(mViewPager, topNewsList)
                mViewPager.setCurrentItem(topNewsList.size * 100, false) // 设置一个较大的初始位置，使得用户可以向左滑动
                bannerVpHelper.startRun()
                onPageChange()
                return RvNewsTopViewHolder(view)
            }

            //正常新闻列表
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
                return RvNewsViewHolder(view)
            }
        }

    }

    // 定义一个变量，用来记录上一次的positionOffset值
    private var lastPositionOffset = 0f

    private fun onPageChange() {
        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    //拖拽事件
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        bannerVpHelper.pauseLoop()
                    }
                    //手指松开的事件
                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        bannerVpHelper.resumeLoop()
                    }

                    else -> {
                    }
                }
            }
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is RvNewsTopViewHolder -> {
            }

            is RvNewsViewHolder -> {
                val itemData = getItem(position)
                holder.bind(itemData)
            }
        }

    }


    sealed class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class RvNewsTopViewHolder(itemView: View) : BaseViewHolder(itemView) {

    }

    inner class RvNewsViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.news_title)
        private val tvName: TextView = itemView.findViewById(R.id.news_hint)
        private val imageView: ImageView = itemView.findViewById(R.id.news_imageView)
        private val tvTime: TextView = itemView.findViewById(R.id.news_time)
        fun bind(itemData: Story) {
            itemView.apply {
                tvName.text = itemData.hint
                titleView.text = itemData.title
                tvTime.text = itemData.ga_prefix
            }
            Glide.with(itemView.context).load(itemData.images[0])
                .transform(CenterCrop(), RoundedCorners(10))//设置圆角
                .into(imageView)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                //设置标志符
                intent.putExtra("topNewsUrl", itemData.url)
                intent.putExtra("newsId", itemData.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}