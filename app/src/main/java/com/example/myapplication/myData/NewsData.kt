package com.example.myapplication.myData

/**
 * description ：负责轮播图和消息的数据接收类
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/29 16:43
 * version: 1.0
 */
data class RecentNewsData<T>(
    val date: String,
    val stories: List<Story>,
    val top_stories: List<TopStory>
)

data class Story(
    val ga_prefix: String,
    val hint: String,
    val id: Int,
    val image_hue: String,
    val images: List<String>,
    val title: String,
    val type: Int,
    val url: String
)

data class TopStory(
    val ga_prefix: String,
    val hint: String,
    val id: Int,
    val image: String,
    val image_hue: String,
    val title: String,
    val type: Int,
    val url: String
)