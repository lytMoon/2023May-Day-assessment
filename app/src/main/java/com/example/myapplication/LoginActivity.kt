package com.example.myapplication

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    //懒加载注入databinding
    private val mBinding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.imNight.setOnClickListener {
            Toast.makeText(this, "功能还在完善中", Toast.LENGTH_SHORT).show()
        }
        mBinding.imWeibo.setOnClickListener {
            Toast.makeText(this, "功能还在完善中", Toast.LENGTH_SHORT).show()
        }
        mBinding.imZhihu.setOnClickListener {
            Toast.makeText(this, "功能还在完善中", Toast.LENGTH_SHORT).show()
        }
        /**
         * 下面实现夜间模式，采用安卓官方的方法,不需要再重建activity
         */
        mBinding.imNight.setOnClickListener {
            Toast.makeText(this, "功能还在完善中", Toast.LENGTH_SHORT).show()
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    // 切换到夜间模式
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    // 切换到白天模式
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }


    }
}