package com.example.myapplication.commentAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.myData.Comment

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:57
 * version: 1.0
 */
class CmNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     val cmImage: ImageView  = itemView.findViewById(R.id.cm_image)
     val cmName :TextView  = itemView.findViewById(R.id.cm_name)
     val cmWhat: TextView = itemView.findViewById(R.id.cm_what)

    fun bind(news: Comment) {
        cmName.text=news.author
        cmWhat.text = news.content
        Glide.with(itemView.context).load(news.avatar).into(cmImage)


//        itemView.setOnClickListener {
//            val intent = Intent(itemView.context, newsReadingActivity::class.java)
//            //设置标志符
//            itemView.context.startActivity(intent)
//        }
    }
}

