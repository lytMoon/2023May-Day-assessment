package com.example.myapplication.topNewsAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.myData.TopStory

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 09:07
 * version: 1.0
 */
class NewsDiffCallback : DiffUtil.ItemCallback<TopStory>() {
    override fun areItemsTheSame(oldItem: TopStory, newItem: TopStory): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TopStory, newItem: TopStory): Boolean {
        return oldItem.title == newItem.title
    }
}