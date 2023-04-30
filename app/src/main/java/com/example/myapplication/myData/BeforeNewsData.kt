package com.example.myapplication.myData

/**
 * description ：用于请求以往的数据库的dataBeen
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/5/1 00:19
 * version: 1.0
 */
data class BeforeNewsData<T>(
    val date: String,
    val stories: List<BeforeStory>
)

data class BeforeStory(
    val ga_prefix: String,
    val hint: String,
    val id: Int,
    val image_hue: String,
    val images: List<String>,
    val title: String,
    val type: Int,
    val url: String
)