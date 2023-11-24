package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.myData.Story

/**
 * description ：辅助新闻详情左右滑动的adapter
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/5/3
 * version: 1.0
 */
class DetailNewsAdapter : ListAdapter<Story, DetailNewsAdapter.DetailNewsViewHolder>(object :
    DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.title == newItem.title && oldItem.id == newItem.id
    }
}) {


    private var mClick: ((Int) -> Unit?)? = null

    fun setOnItemClick(m: ((Int) -> Unit?)) {
        mClick = m
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_webview, parent, false)
        return DetailNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailNewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    inner class DetailNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val detailWebView: WebView = itemView.findViewById(R.id.webView)
        fun bind(news: Story) {
            detailWebView.loadUrl(news.url)
        }
    }
}