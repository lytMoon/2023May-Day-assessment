package com.example.myapplication.detailAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.myData.Story

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/5/3
 * version: 1.0
 */
class DetailNewsAdapter : ListAdapter<Story, DetailNewsViewHolder>(DetailNewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_webview, parent, false)
        return DetailNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailNewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

}