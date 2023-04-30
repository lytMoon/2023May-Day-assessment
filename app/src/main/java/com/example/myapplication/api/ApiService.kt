package com.example.myapplication.api

import com.example.myapplication.myData.BeforeNewsData
import com.example.myapplication.myData.BeforeStory
import com.example.myapplication.myData.RecentNewsData
import com.example.myapplication.myData.Story
import com.example.myapplication.myData.TopStory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * description ：这是一个为retrofit提供服务的apiService
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

    /**
    下面是进行最新消息的请求
     */
    @GET("api/4/news/latest")
    fun getRecentNews(): Call<RecentNewsData<Story>>

    /**
     * 下面进行更早的新闻消息的网络请求
     * https://news-at.zhihu.com/api/4/news/before/20230425
     */
    @GET("api/4/news/before/{time}")
    fun getBeforeNews(@Path("time")time:String): Call<BeforeNewsData<BeforeStory>>




}