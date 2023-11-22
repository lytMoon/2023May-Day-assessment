package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.myData.Comment

/**
 * description ：负责展示评论区的adapter
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/30 08:49
 * version: 1.0
 */
class CmNewsAdapter :
    ListAdapter<Comment, CmNewsAdapter.CmNewsViewHolder>(object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.content == newItem.content
        }
    }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CmNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CmNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CmNewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }


    class CmNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cmImage: ImageView = itemView.findViewById(R.id.cm_image)
        private val cmName: TextView = itemView.findViewById(R.id.cm_name)
        private val cmWhat: TextView = itemView.findViewById(R.id.cm_what)


        fun bind(news: Comment) {
            cmName.text = news.author
            cmWhat.text = news.content
            Glide.with(itemView.context).load(news.avatar).into(cmImage)
        }
    }


}