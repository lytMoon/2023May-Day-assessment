package com.example.myapplication
import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.ApiService
import com.example.myapplication.myData.Comment
import com.example.myapplication.myData.CommentData
import com.example.myapplication.myData.RecentNewsData
import com.example.myapplication.myData.Story
import com.example.myapplication.myData.TopStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections.addAll

/**
 * description ：主要负责网络请求，把数据传入livedata，livedata可被外界观察到
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/29 16:47
 * version: 1.0
 */
class MyViewModel:androidx.lifecycle.ViewModel() {

    // 下面进行网络请求
    private val _newsTopData: MutableLiveData<List<TopStory>> = MutableLiveData()
    private val _commentData: MutableLiveData<List<Comment>> = MutableLiveData()
    private val _newsRecentData: MutableLiveData<List<Story>> = MutableLiveData()
    private val _BeforeNewsData: MutableLiveData<List<Story>> = MutableLiveData()
    private val _mergedLiveData = MediatorLiveData<List<Story>>()
    val newsTopData: LiveData<List<TopStory>>
        get() = _newsTopData//
    val newsRecentData: LiveData<List<Story>>
        get() = _newsRecentData
    val newsBeforeData: LiveData<List<Story>>
        get() = _BeforeNewsData
    val commentData: LiveData<List<Comment>>
        get() = _commentData
    val mergedLiveData : MediatorLiveData<List<Story>>
        get() = _mergedLiveData


    /**
     * 下面进行getTopnews（轮播图的网络请求）
     */
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
                Log.d("999", "(MyViewModel.kt:43)-->> $_newsTopData")
            }
            override fun onFailure(call: Call<RecentNewsData<TopStory>>, t: Throwable) {
                Log.d("1000","网络请求失败了"+t.message);
            }
        })
    }

    /**
     * 下面是getRecentNews
     */
    fun rnRecentStoryQuest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://news-at.zhihu.com/")
            .addConverterFactory(GsonConverterFactory.create())//指明解析数据时进行的转换库
            .build()
        val apiService= retrofit.create(ApiService::class.java)
        apiService.getRecentNews().enqueue(object: Callback<RecentNewsData<Story>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<RecentNewsData<Story>>,
                response: Response<RecentNewsData<Story>>
            ) {
                _newsRecentData.postValue(response.body()?.stories!!)//得到的是一个top_stories类型的对象
                Log.d("999", "(MyViewModel.kt:43)-->> $_newsRecentData")
            }
            override fun onFailure(call: Call<RecentNewsData<Story>>, t: Throwable) {
                Log.d("1000","网络请求失败了"+t.message);
            }


        })
    }

    /**
     * 对过往的网络数据进行请求
     */
    fun rvBeforeStoryQuest(time:String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://news-at.zhihu.com/")
            .addConverterFactory(GsonConverterFactory.create())//指明解析数据时进行的转换库
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getBeforeNews(time).enqueue(object : Callback<RecentNewsData<Story>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<RecentNewsData<Story>>,
                response: Response<RecentNewsData<Story>>
            ) {
                val newDataList =response.body()?.stories!!

               _newsRecentData.postValue(response.body()?.stories!!)//得到的是一个top_stories类型的对象
                val list = response.body()?.stories!!//得到的是一个top_stories类型的对象
                for (it in list) {
                    Log.d("987", "(MyViewModel.kt:101)-->> " + it.url);
                }
            }

            override fun onFailure(call: Call<RecentNewsData<Story>>, t: Throwable) {
                Log.d("1000", "网络请求失败了" + t.message);
            }
        })


//    fun mergeData(){
//        _mergedLiveData.addSource(_newsRecentData) { stories ->
//            val currentList = _mergedLiveData.value ?: emptyList()
//            _mergedLiveData.value = currentList + stories
//        }
//
//        _mergedLiveData.addSource(_BeforeNewsData) { stories ->
//            val currentList = _mergedLiveData.value ?: emptyList()
//            _mergedLiveData.value = currentList + stories
//        }
//
//    }
    }



    fun rvCommentQuest(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://news-at.zhihu.com/")
            .addConverterFactory(GsonConverterFactory.create())//指明解析数据时进行的转换库
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getCommentNews(id).enqueue(object : Callback<CommentData<Comment>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<CommentData<Comment>>,
                response: Response<CommentData<Comment>>
            ) {
                _commentData.postValue(response.body()?.comments!!)//得到的是一个top_stories类型的对象
            }
            override fun onFailure(call: Call<CommentData<Comment>>, t: Throwable) {
                Log.d("1000", "网络请求失败了" + t.message);
            }
        })
    }
}






