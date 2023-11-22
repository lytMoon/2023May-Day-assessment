package com.example.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.ui.DetailActivity
import com.example.myapplication.R
import com.example.myapplication.myData.Story

/**
 * description ：展示新闻列表
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:49
 * version: 1.0
 */
class RvNewsAdapter : ListAdapter<Story, RvNewsAdapter.RvNewsViewHolder>(object : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.title == newItem.title
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return RvNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RvNewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    inner class RvNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.news_title)
        private val tvName: TextView = itemView.findViewById(R.id.news_hint)
        private val imageView: ImageView = itemView.findViewById(R.id.news_imageView)
        private val tvTime: TextView = itemView.findViewById(R.id.news_time)
        fun bind(news: Story) {
            tvName.text = news.hint
            titleView.text = news.title
            tvTime.text = news.ga_prefix
            //通过使用glide来对图片进行绑定和一些处理
            Glide.with(itemView.context)
                .load(news.images[0])
                .transform(CenterCrop(), RoundedCorners(10))//设置圆角
                .into(imageView)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                //设置标志符
                intent.putExtra("topNewsUrl", news.url)
                intent.putExtra("newsId", news.id)
                itemView.context.startActivity(intent)
            }
        }
    }


}