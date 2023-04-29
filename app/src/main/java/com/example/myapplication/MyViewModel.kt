package com.example.myapplication

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.ApiService
import com.example.myapplication.myData.RecentNewsData
import com.example.myapplication.myData.TopStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/29 16:47
 * version: 1.0
 */
class MyViewModel:androidx.lifecycle.ViewModel() {
    // 下面进行网络请求
    private val _userData: MutableLiveData<List<TopStory>> = MutableLiveData()
    val userData: LiveData<List<TopStory>>
        get() = _userData//userData只是可被外界观察到的

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
                _userData.postValue(response.body()?.top_stories!!)
                Log.d("999","(MyViewModel.kt:43)-->> "+_userData.toString());
                val list = response.body()?.top_stories//得到list对象，自己解析为一个对象可以使用it来遍历
                if (list!=null){
                    for (it in list){
                        //这里进行最终的日志打印
                        Log.d("1000","(MainActivity.kt:54)-->> "+it.toString());

                    }
                }
            }

            override fun onFailure(call: Call<RecentNewsData<TopStory>>, t: Throwable) {
                Log.d("1000","网络请求失败了"+t.message);
            }

        })
    }
}