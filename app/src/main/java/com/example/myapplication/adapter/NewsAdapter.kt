package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.R
import com.example.myapplication.helper.BannerVpHelper
import com.example.myapplication.myData.Story

/**
 * description ：展示新闻列表
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:49
 * version: 1.0
 */
class NewsAdapter() : ListAdapter<Story, ViewHolder>(object : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.title == newItem.title
    }
}) {


    //默认是没有滑动
    private var isScrolling = false
    lateinit var bannerVpHelper: BannerVpHelper
    val viewPager2Adapter: BannerAdapter = BannerAdapter()

    private lateinit var bannerNewsList: List<Story>
    private lateinit var mViewPager: ViewPager2

    /**
     * 点击按钮的回调
     */

    private var mClick: ((Int) -> Unit)? = null


    fun setOnItemClick(listener: (Int) -> Unit) {
        mClick = listener
    }


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
                    LayoutInflater.from(parent.context).inflate(R.layout.item_top_rv, parent, false)
                mViewPager = view.findViewById(R.id.banner_view_pager)
                initBannerAdapter()
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

    //初始化Banner的Adapter
    private fun initBannerAdapter() {
        bannerVpHelper = BannerVpHelper(mViewPager, bannerNewsList)
        bannerVpHelper.startRun()
        viewPager2Adapter.submitList(bannerNewsList)
        mViewPager.adapter = viewPager2Adapter
        onPageChange()
    }

    fun submitBannerList(bannerNewsList: List<Story>) {
        this.bannerNewsList = bannerNewsList
    }

    //检测手指的滑动事件
    private fun onPageChange() {

        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            var n = 0
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    //拖拽事件
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        n++
                        Log.d("484848", "NewsAdapter.kt:------")
                        bannerVpHelper.pauseLoop()
                        isScrolling = true
                        viewPager2Adapter.addPageChange(true)

                    }
                    //手指松开的事件
                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        bannerVpHelper.resumeLoop()
                        isScrolling = false
                        viewPager2Adapter.addPageChange(false)
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
                    mViewPager.setCurrentItem(bannerNewsList.size * 100, false)
                }
                /**
                 * 这里为了防止0过快的把current覆盖，所以做了一个判断，必须让position不能为0的时候才进行值的传递。及时更新
                 */
                if (position != 0) {
                    bannerVpHelper.changeCurrentPage(position)
                }
            }
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is RvNewsTopViewHolder -> {}

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
//        private val tvTime: TextView = itemView.findViewById(R.id.news_time)


        init {
            itemView.setOnClickListener {
                if (!isScrolling) {
                    mClick?.invoke(absoluteAdapterPosition)
                }
            }

        }

        fun bind(itemData: Story) {
            itemView.apply {
                tvName.text = itemData.hint
                titleView.text = itemData.title
//                tvTime.text = itemData.ga_prefix
            }
            Glide.with(itemView.context).load(itemData.images[0])
                .transform(CenterCrop(), RoundedCorners(10))//设置圆角
                .into(imageView)

        }
    }
}