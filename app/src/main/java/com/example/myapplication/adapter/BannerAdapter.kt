package com.example.myapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.myData.Story

/**
 * description
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:49
 * version: 1.0
 */
class BannerAdapter :
    ListAdapter<Story, BannerAdapter.NewsViewHolder>(object :
        DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.title == newItem.title
        }
    }) {


    //默认是没有滑动
    private var isScrolling = false

    /**
     * 点击按钮的回调
     */
    private var mClick: ((Int) -> Unit)? = null

    fun setOnItemClick(listener: (Int) -> Unit) {
        mClick = listener
    }


    fun addPageChange(isl: Boolean) {
        isScrolling = isl
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE // 返回一个很大的值，使得用户无法滑动到边界
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.d("576868687675", "BannerAdapter.kt:------>${position}")
        val news = getItem(position % currentList.size)
        holder.bind(news)
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.titleView)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener {
                if (!isScrolling) {
                    mClick?.invoke(absoluteAdapterPosition % currentList.size)
                }
            }
        }

        fun bind(news: Story) {
            tvName.text = news.hint
            titleView.text = news.title
            Glide.with(itemView.context).load(news.image).into(imageView)
        }
    }
}
