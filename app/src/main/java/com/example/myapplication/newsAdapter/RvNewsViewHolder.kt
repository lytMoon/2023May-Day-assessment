package com.example.myapplication.newsAdapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.R
import com.example.myapplication.myData.Story
import com.example.myapplication.NewsReadingActivity

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:57
 * version: 1.0
 */
class RvNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     val titleView: TextView  = itemView.findViewById(R.id.news_title)
     val tvName :TextView  = itemView.findViewById(R.id.news_hint)
     val imageView: ImageView = itemView.findViewById(R.id.news_imageView)
     val tvTime:TextView =itemView.findViewById(R.id.news_time)
    fun bind(news: Story) {
        tvName.text=news.hint
        titleView.text = news.title
        tvTime.text=news.ga_prefix
        //通过使用glide来对图片进行绑定和一些处理
        Glide.with(itemView.context)
            .load(news.images.get(0))
            .transform(CenterCrop(), RoundedCorners(10))//设置圆角
            .into(imageView)
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, NewsReadingActivity::class.java)
            //设置标志符
            intent.putExtra("topNewsUrl", news.url)
            intent.putExtra("newsId", news.id)
            itemView.context.startActivity(intent)
        }
    }
}

