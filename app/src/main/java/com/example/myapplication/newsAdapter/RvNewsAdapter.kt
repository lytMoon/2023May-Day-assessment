package com.example.myapplication.newsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.myData.Story
import com.example.myapplication.myData.TopStory

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:49
 * version: 1.0
 */
class RvNewsAdapter : ListAdapter<Story, RvNewsViewHolder>(RvNewsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return RvNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RvNewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }
}