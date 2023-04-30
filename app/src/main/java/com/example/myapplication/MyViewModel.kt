package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.example.myapplication.api.ApiService
import com.example.myapplication.myData.RecentNewsData
import com.example.myapplication.myData.TopStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/29 16:47
 * version: 1.0
 */
class MyViewModel:androidx.lifecycle.ViewModel() {
    // 下面进行网络请求
    private val _newsTopData: MutableLiveData<List<TopStory>> = MutableLiveData()
    val newsTopData: LiveData<List<TopStory>>
        get() = _newsTopData//
    fun rnTopStorySendQuest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://news-at.zhihu.com/")
            .addConverterFactory(GsonConverterFactory.create())//指明解析数据时进行的转换库
            .build()
        val apiService= retrofit.create(ApiService::class.java)
        apiService.getRecentTopNews().enqueue(object: Callback<RecentNewsData<TopStory>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<RecentNewsData<TopStory>>,
                response: Response<RecentNewsData<TopStory>>
            ) {
                _newsTopData.postValue(response.body()?.top_stories!!)//得到的是一个top_stories类型的对象
                Log.d("999","(MyViewModel.kt:43)-->> "+_newsTopData.toString())
            }
            override fun onFailure(call: Call<RecentNewsData<TopStory>>, t: Throwable) {
                Log.d("1000","网络请求失败了"+t.message);
            }
        })
    }
}


