package com.example.myapplication.util

import com.example.myapplication.api.ApiService
import com.example.myapplication.myData.Comment
import com.example.myapplication.myData.CommentData
import com.example.myapplication.myData.RecentNewsData
import com.example.myapplication.myData.Story
import com.example.myapplication.myData.TopStory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetObj {


    private const val baseUrl = "https://news-at.zhihu.com/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())//指明解析数据时进行的转换库
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private fun <T> create(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    private val apiService: ApiService = create(ApiService::class.java)


    fun getTopNews(): Observable<RecentNewsData<TopStory>> {
        return apiService.getTopNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNews(): Observable<RecentNewsData<Story>> {
        return apiService.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getBeforeNews(time: String): Observable<RecentNewsData<Story>> {
        return apiService.getBeforeNews(time)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getComments(id: Int): Observable<CommentData<Comment>> {
        return apiService.getCommentNews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}