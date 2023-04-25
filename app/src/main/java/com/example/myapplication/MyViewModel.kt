package com.example.myapplication

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.api.ApiService
import com.example.myapplication.myData.UserData
import com.example.myapplication.myData.UserList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ：我们把网络请求放到viewmodel中
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/25 22:27
 * version: 1.0
 */
class MyViewModel : ViewModel() {
    // TODO: 下面进行user的网络请求。微信公众号。

    private fun UserSendRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())//指明解析数据时进行的转换库
            .build()
        val apiService= retrofit.create(ApiService::class.java)
        apiService.getwxarticle().enqueue(object: Callback<UserList<UserData>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<UserList<UserData>>,
                response: Response<UserList<UserData>>
            ) {
                val list = response.body()?.data//得到list对象，自己解析为一个对象可以使用it来遍历
                if (list!=null){
                    for (it in list){
                        //这里进行最终的日志打印
                        Log.d("999","(MainActivity.kt:54)-->> "+it.name);

                    }
                }
            }

            override fun onFailure(call: Call<UserList<UserData>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}