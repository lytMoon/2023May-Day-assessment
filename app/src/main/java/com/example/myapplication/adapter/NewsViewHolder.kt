package com.example.myapplication.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.myData.TopStory
import com.example.myapplication.newsReadingActivity

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:57
 * version: 1.0
 */
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     val titleView: TextView  = itemView.findViewById(R.id.titleView)
     val tvName :TextView  = itemView.findViewById(R.id.tv_name)
     val imageView: ImageView = itemView.findViewById(R.id.imageView)

    fun bind(news: TopStory) {
        tvName.text=news.hint
        titleView.text = news.title
        Glide.with(itemView.context).load(news.image).into(imageView)


        itemView.setOnClickListener {
            val intent = Intent(itemView.context, newsReadingActivity::class.java)
            //设置标志符
            intent.putExtra("topNewsUrl", news.url)
            itemView.context.startActivity(intent)
        }
    }
}

