package com.example.myapplication.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.viewmodel.MyViewModel
import com.example.myapplication.databinding.ActivityNewsReadingBinding
import com.example.myapplication.adapter.DetailNewsAdapter
import com.example.myapplication.myData.Story
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 这是一个主要负责阅读查看器的activity
 */
class DetailActivity : AppCompatActivity() {
    private val myViewModel by lazy {
        ViewModelProvider(MainActivity())[MyViewModel::class.java]
    }
    private val mBinding: ActivityNewsReadingBinding by lazy {
        ActivityNewsReadingBinding.inflate(layoutInflater)
    }
    private val adapter = DetailNewsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        /***
         *
         * 获得livedData
         */
        // 获取 SharedPreferences 对象
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 从 SharedPreferences 中读取 LiveData 对象的值，并将其转换为 List<Story> 类型
        val listAsString = sharedPreferences.getString("all_list_key", null)
        val listType = object : TypeToken<List<Story>>() {}.type

        val allList: MutableList<Story> =
            Gson().fromJson(listAsString, object : TypeToken<MutableList<Story>>() {}.type)
        adapter.submitList(allList)
        mBinding.detailViewpager2.adapter = adapter
        val topNewsUrl = intent.getStringExtra("topNewsUrl")
        val cmId = intent.getIntExtra("newsId", 0)
        Log.d("cm1222", "(newsReadingActivity.kt:25)-->> $cmId")
        Log.d("cm1222", "(newsReadingActivity.kt:17)-->> $topNewsUrl")
        val currentList = ArrayList<Int>()
        for (it in allList) {
            currentList.add(it.id)
            Log.d("585858589", "DetailActivity.kt:------>${allList}")
        }
        val position = currentList.indexOf(cmId)
        mBinding.detailViewpager2.currentItem = position
        mBinding.btnReturn.setOnClickListener {
            Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show()
            finish()//结束当前的activity
        }
        mBinding.commentButton.setOnClickListener {
            Toast.makeText(this, "查看评论", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CommentActivity::class.java)
            //设置标志符
            intent.putExtra("commentID", cmId)
            startActivity(intent)

        }

        mBinding.favoriteButton.setOnClickListener {
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show()
        }

        mBinding.shareButton.setOnClickListener {
            Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show()
            //下面是采用系统的分享工具
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"//指定类型是纯文本分享
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享标题")
            intent.putExtra(Intent.EXTRA_TEXT, topNewsUrl)
            startActivity(Intent.createChooser(intent, "分享到"))
        }


        /**
         * 下面是左右滑动的事件检测，已经检测到了，但是没有处理具体数据分配
         */
//    //todo：下面是左右滑动的事件检测，已经检测到了，但是没有处理具体数据分配
//    private fun goBefore() {
//        Toast.makeText(this,"很抱歉，虽然检测到了滑动，但是功能还没有实现",Toast.LENGTH_SHORT).show()
//
//        // 在另一个 Activity 中获取 MainActivity 中的 ViewModel 对象
//        //val viewModel = ViewModelProvider(MainActivity.instance).get(MyViewModel::class.java)
//        val topNewsUrl = intent.getStringExtra("topNewsUrl")
//        //Toast.makeText(this,topNewsUrl,Toast.LENGTH_SHORT).show()
////        myViewModel.newsRecentData.observe(this){ list  ->
////            for (it in list){
////                Toast.makeText(this,it.url,Toast.LENGTH_SHORT).show()
////
////         }
////        }
//
//    }

//    private fun goForward() {
//        Toast.makeText(this,"很抱歉，虽然检测到了滑动，但是功能还没有实现",Toast.LENGTH_SHORT).show()
//
//    }
    }


}

private fun <E> MutableList<E>.add(element: Int) {

}


