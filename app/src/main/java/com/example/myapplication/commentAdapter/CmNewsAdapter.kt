package com.example.myapplication.commentAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.myData.Comment
import com.example.myapplication.myData.TopStory

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:49
 * version: 1.0
 */
class CmNewsAdapter : ListAdapter<Comment, CmNewsViewHolder>(CmNewsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CmNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CmNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CmNewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }
}