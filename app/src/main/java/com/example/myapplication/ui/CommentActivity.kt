package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.MyViewModel
import com.example.myapplication.adapter.CmNewsAdapter
import com.example.myapplication.databinding.ActivityCommentBinding
import com.example.myapplication.databinding.ActivityMainBinding

class CommentActivity : AppCompatActivity() {

    private val myViewModel by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }


    private val mBinding: ActivityCommentBinding by lazy {
        ActivityCommentBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        val commentID = intent.getIntExtra("commentID", 0)
        Log.d("commentID", "(CommentActivity.kt:26)-->> $commentID");

        myViewModel.rvCommentQuest(commentID)
        /**
         * 下面是来展示评论区
         */

        val rv_viewPager: RecyclerView = mBinding.rvComment
        mBinding.rvComment.layoutManager = LinearLayoutManager(this)//设置线性布局
        val rv_adapter = CmNewsAdapter()
        myViewModel.commentData.observe(this) { it ->
            rv_adapter.submitList(it)
        }
        rv_viewPager.adapter = rv_adapter

        /**
         * 下面是一个监听事件
         */
        mBinding.cmReturn.setOnClickListener {
            finish()
        }


    }
}