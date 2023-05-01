package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.commentAdapter.CmNewsAdapter
import com.example.myapplication.databinding.ActivityCommentBinding
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.newsAdapter.RvNewsAdapter

class CommentActivity : AppCompatActivity() {
    //懒加载注入viewmodel
    private val myViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }
    //懒加载注入databinding
    private val mBinding: ActivityCommentBinding by lazy { ActivityCommentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        val commentID=intent.getIntExtra("commentID",0)
        Log.d("commentID", "(CommentActivity.kt:26)-->> $commentID");

        myViewModel.rvCommentQuest(commentID)
        /**
         * 下面是来展示评论区
         */
        myViewModel.rnRecentStoryQuest()

        val rv_viewPager: RecyclerView = mBinding.rvComment
        mBinding.rvComment.layoutManager = LinearLayoutManager(this)//设置线性布局
        val rv_adapter = CmNewsAdapter()
        myViewModel.commentData.observe(this) { it ->
            rv_adapter.submitList(it)
        }
        rv_viewPager.adapter=rv_adapter

        /**
         * 下面是一个监听事件
         */
        mBinding.cmReturn.setOnClickListener {
            finish()
        }


    }
}