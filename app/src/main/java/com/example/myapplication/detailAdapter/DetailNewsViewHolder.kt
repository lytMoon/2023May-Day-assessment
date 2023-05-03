package com.example.myapplication.detailAdapter
import android.view.View
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.myData.Story
/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/5/3
 * version: 1.0
 */
class DetailNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val detailWebView : WebView =itemView.findViewById(R.id.webView)
    fun bind(news: Story) {
        detailWebView.loadUrl(news.url)
        itemView.setOnClickListener {


        }
    }
}

