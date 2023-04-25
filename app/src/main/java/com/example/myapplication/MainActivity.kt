package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.api.ApiService
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.myData.UserData
import com.example.myapplication.myData.UserList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    //懒加载注入databinding
    private val mBinding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        onClick()
    }

    private fun onClick() {
        mBinding.btn.setOnClickListener {
            sendRequest()
        }
    }

    // TODO: 下面用retrofit来进行网络请求
    private fun sendRequest() {
        val retrofit =Retrofit.Builder()
            .baseUrl("https://wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())//指明解析数据时进行的转换库
            .build()
        val apiService= retrofit.create(ApiService::class.java)
        apiService.getwxarticle().enqueue(object:Callback<UserList<UserData>>{
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


    //下面是使用okp进行的网络请求（retrofit不熟练的时候使用的）
//    @SuppressLint("SuspiciousIndentation")
//    private fun sendRequest() {
//        thread {
//            try {
//            val client = OkHttpClient()
//            val request = Request.Builder()
//                .url("https://wanandroid.com/wxarticle/chapters/json")
//                .get()
//                .build()
//            val response =client.newCall(request).execute()
//            val responseData = response.body()?.string()
//                Log.d("666","(MainActivity.kt:40)-->> "+responseData);
//            if (responseData!=null){
//                parseJSONWithGSON(responseData)
//            } }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun parseJSONWithGSON(jsonData: String) {
//        val gson = Gson()
//        val typeOf =object : TypeToken<UserList<UserData>>(){}.type//设置数据需要解析成的类型
//        val myList = gson.fromJson<UserList<UserData>>(jsonData,typeOf)
//        val myfinalList= myList.data
//        for (it in myfinalList){
//            Log.d("888","(MainActivity.kt:57)-->> "+it.name);
//
//        }
//    }


}



