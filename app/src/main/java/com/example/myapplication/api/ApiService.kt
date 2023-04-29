package com.example.myapplication.api

import com.example.myapplication.myData.RecentNewsData
import com.example.myapplication.myData.Story
import com.example.myapplication.myData.TopStory
import retrofit2.Call
import retrofit2.http.GET

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/29 16:28
 * version: 1.0
 */
interface ApiService {

    /**
      下面是最新的新闻消息，用于我们的轮播图
     */
    @GET("api/4/news/latest")
    fun getRecentTopNews(): Call<RecentNewsData<TopStory>>



}