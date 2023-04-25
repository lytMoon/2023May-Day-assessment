package com.example.myapplication.myData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/24 18:00
 * version: 1.0
 */

 data class UserList<T>(
    val `data`: List<UserData>,
    val errorCode: Int,
    val errorMsg: String
)

data class UserData(
    val articleList: List<Any>,
    val author: String,
    val children: List<Any>,
    val courseId: Int,
    val cover: String,
    val desc: String,
    val id: Int,
    val lisense: String,
    val lisenseLink: String,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val type: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)