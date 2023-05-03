package com.example.myapplication.detailAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.myData.Story

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/5/3
 * version: 1.0
 */
class DetailNewsDiffCallback : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.title == newItem.title
    }
}