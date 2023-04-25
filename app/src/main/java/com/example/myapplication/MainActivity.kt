package com.example.myapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.myData.UserData
import com.example.myapplication.myData.UserList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
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

    private fun sendRequest() {
        thread {
            try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://wanandroid.com/wxarticle/chapters/json")
                .get()
                .build()
            val response =client.newCall(request).execute()
            val responseData = response.body()?.string()
                Log.d("666","(MainActivity.kt:40)-->> "+responseData);
            if (responseData!=null){
                parseJSONWithGSON(responseData)
            } }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun parseJSONWithGSON(jsonData: String) {
        val gson = Gson()
        val typeOf =object : TypeToken<UserList<UserData>>(){}.type//设置数据需要解析成的类型
        val myList = gson.fromJson<UserList<UserData>>(jsonData,typeOf)
        val myfinalList= myList.data
        for (it in myfinalList){
            Log.d("888","(MainActivity.kt:57)-->> "+it.name);

        }
    }


}

