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
import com.example.myapplication.ui.DetailActivity
import com.example.myapplication.R
import com.example.myapplication.myData.TopStory

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:49
 * version: 1.0
 */
class TopNewsAdapter :
    ListAdapter<TopStory, TopNewsAdapter.NewsViewHolder>(object :
        DiffUtil.ItemCallback<TopStory>() {
        override fun areItemsTheSame(oldItem: TopStory, newItem: TopStory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TopStory, newItem: TopStory): Boolean {
            return oldItem.title == newItem.title
        }
    }) {



    override fun getItemCount(): Int {
        return Int.MAX_VALUE // 返回一个很大的值，使得用户无法滑动到边界
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position % currentList.size)
        holder.bind(news)
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.titleView)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(news: TopStory) {
            tvName.text = news.hint
            titleView.text = news.title
            Glide.with(itemView.context).load(news.image).into(imageView)


            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                //设置标志符
                intent.putExtra("newsId", news.id)
                intent.putExtra("topNewsUrl", news.url)
                itemView.context.startActivity(intent)
            }
        }
    }

}