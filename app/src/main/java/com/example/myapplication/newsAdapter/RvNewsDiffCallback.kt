package com.example.myapplication.newsAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.myData.Story
import com.example.myapplication.myData.TopStory

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 09:07
 * version: 1.0
 */
class RvNewsDiffCallback : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.title == newItem.title
    }
}