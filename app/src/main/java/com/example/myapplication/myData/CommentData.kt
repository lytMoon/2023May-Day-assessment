package com.example.myapplication.myData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/5/1 13:57
 * version: 1.0
 */
data class CommentData<T>(
    val comments: List<Comment>
)

data class Comment(
    val author: String,
    val avatar: String,
    val content: String,
    val id: Int,
    val likes: Int,
    val reply_to: ReplyTo,
    val time: Int
)

data class ReplyTo(
    val author: String,
    val content: String,
    val id: Int,
    val status: Int
)