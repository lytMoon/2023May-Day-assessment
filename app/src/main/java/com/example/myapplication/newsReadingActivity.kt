package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityNewsReadingBinding

/**
 * 这是一个主要负责阅读查看器的activity
 */
class newsReadingActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private val mBinding :ActivityNewsReadingBinding by lazy { ActivityNewsReadingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        webView=mBinding.myWebView
        val topNewsUrl= intent.getStringExtra("topNewsUrl")
        Log.d("topNewsUrl", "(newsReadingActivity.kt:17)-->> $topNewsUrl")
        if (!topNewsUrl.isNullOrEmpty()) {
            webView.loadUrl(topNewsUrl)
        }
        mBinding.btnReturn.setOnClickListener{
            Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show()
            finish()//结束当前的activity
        }
        mBinding.commentButton.setOnClickListener {
            Toast.makeText(this, "查看评论", Toast.LENGTH_SHORT).show()

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

    }
}