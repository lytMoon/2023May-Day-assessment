package com.example.myapplication
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityNewsReadingBinding

/**
 * 这是一个主要负责阅读查看器的activity
 */
class NewsReadingActivity : AppCompatActivity() {
    private val myViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }
    private lateinit var webView: WebView
    private val mBinding: ActivityNewsReadingBinding by lazy { ActivityNewsReadingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        webView = mBinding.myWebView
        val topNewsUrl = intent.getStringExtra("topNewsUrl")
        val cmId = intent.getIntExtra("newsId", 0)//找不到的话那就设置为默认值0
        Log.d("cmId", "(newsReadingActivity.kt:25)-->> $cmId")
        Log.d("topNewsUrl", "(newsReadingActivity.kt:17)-->> $topNewsUrl")
        if (!topNewsUrl.isNullOrEmpty()) {
            webView.loadUrl(topNewsUrl)
        }
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


        mBinding.myWebView.setOnTouchListener(@SuppressLint("ClickableViewAccessibility")
        object : View.OnTouchListener {
            private var startX = 0f

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                    }

                    MotionEvent.ACTION_UP -> {
                        val endX = event.x
                        if (endX - startX > 100) {
                            if (topNewsUrl != null) {
                                goBefore()
                            }
                        } else if (startX - endX > 100) {
                            goForward()
                        }
                    }
                }
                return false
            }


        })
    }

    /**
     * 下面是左右滑动的事件检测，已经检测到了，但是没有处理具体数据分配
     */
    //todo：下面是左右滑动的事件检测，已经检测到了，但是没有处理具体数据分配
    private fun goBefore() {
        Toast.makeText(this,"很抱歉，虽然检测到了滑动，但是功能还没有实现",Toast.LENGTH_SHORT).show()

        // 在另一个 Activity 中获取 MainActivity 中的 ViewModel 对象
        //val viewModel = ViewModelProvider(MainActivity.instance).get(MyViewModel::class.java)
        val topNewsUrl = intent.getStringExtra("topNewsUrl")
        //Toast.makeText(this,topNewsUrl,Toast.LENGTH_SHORT).show()
//        myViewModel.newsRecentData.observe(this){ list  ->
//            for (it in list){
//                Toast.makeText(this,it.url,Toast.LENGTH_SHORT).show()
//
//         }
//        }

    }

    private fun goForward() {
        Toast.makeText(this,"很抱歉，虽然检测到了滑动，但是功能还没有实现",Toast.LENGTH_SHORT).show()

    }
}


