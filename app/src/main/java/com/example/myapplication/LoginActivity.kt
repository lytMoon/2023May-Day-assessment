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
        //Todo:夜间模式
        mBinding.imNight.setOnClickListener {
            Toast.makeText(this, "功能还在完善中", Toast.LENGTH_SHORT).show()

//            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
//            when (currentNightMode) {
//                Configuration.UI_MODE_NIGHT_NO -> {
//                    // 切换到夜间模式
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                }
//                Configuration.UI_MODE_NIGHT_YES -> {
//                    // 切换到白天模式
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                }
//            }
//            recreate() // 重新创建Activity以应用新主题
        }


    }
}