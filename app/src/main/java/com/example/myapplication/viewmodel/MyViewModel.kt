package com.example.myapplication.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.myData.Comment
import com.example.myapplication.myData.RecentNewsData
import com.example.myapplication.myData.Story
import com.example.myapplication.util.NetObj
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ：主要负责网络请求，把数据传入livedata，livedata可被外界观察到
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/4/29 16:47
 * version: 1.0
 */
class MyViewModel : ViewModel() {


    private val _newsTopData: MutableLiveData<List<Story>> = MutableLiveData()
    private val _commentData: MutableLiveData<List<Comment>> = MutableLiveData()
    private val _newsRecentData: MutableLiveData<List<Story>> = MutableLiveData()

    init {
        receiveTopNews()
        receiveNewsData()
    }

    val newsTopData: LiveData<List<Story>>
        get() = _newsTopData//
    val newsRecentData: LiveData<List<Story>>
        get() = _newsRecentData
    val commentData: LiveData<List<Comment>>
        get() = _commentData

    /**
     * 获取轮播图数据
     */
     fun receiveTopNews() {

        NetObj.getTopNews()
            .subscribe(object : Observer<RecentNewsData<Story>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d("testLog", "MyViewModel.kt:------>${e}")
                }

                override fun onComplete() {
                }

                override fun onNext(t: RecentNewsData<Story>) {
                    _newsTopData.postValue(t.top_stories)
                }

            })
    }

    /**
     * 获取初始显示的新闻数据
     */
     fun receiveNewsData() {
        NetObj.getNews().subscribe(object : Observer<RecentNewsData<Story>> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                Log.d("testLog", "MyViewModel.kt:------>${e}")
            }

            override fun onComplete() {
            }

            override fun onNext(t: RecentNewsData<Story>) {
                _newsRecentData.postValue(t.stories)
            }

        })
    }

    /**
     * 对过往的新闻数据进行请求
     */
    @SuppressLint("CheckResult")
    fun rvBeforeStoryQuest(time: String) {
        NetObj.getBeforeNews(time)
            .subscribe(object : Observer<RecentNewsData<Story>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Log.d("testLog", "MyViewModel.kt:------>${e}")
                }

                override fun onComplete() {
                }

                override fun onNext(t: RecentNewsData<Story>) {
                    _newsRecentData.value = _newsRecentData.value?.plus(t.stories)
                    Log.d("48484884","MyViewModel.kt:------>${t.stories}")
                    //等价于下面
//                    val newData = t.stories
//                    val oldData = _newsRecentData.value ?: emptyList()//为空的时候返回emptyList
//                    _newsRecentData.value = oldData + newData// 将新数据添加到旧数据列表中，然后设置为 LiveData 的值
                }

            })
    }


    @SuppressLint("CheckResult")
    fun rvCommentQuest(id: Int) {
        NetObj.getComments(id)
            .subscribe {
                _commentData.postValue(it.comments)
            }
    }
}






