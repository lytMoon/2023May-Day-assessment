package com.example.myapplication.api

import com.example.myapplication.myData.UserData
import com.example.myapplication.myData.UserList
import retrofit2.Call
import retrofit2.http.GET

/**
 * description ：用于retrofit的网络请求
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/25 21:44
 * version: 1.0
 */
interface ApiService {
    /**
     * 微信公众号列表
     * https://wanandroid.com/wxarticle/chapters/json
     * name关键字可以调用
     */
    @GET("wxarticle/chapters/json")
    fun getwxarticle(): Call<UserList<UserData>>


}