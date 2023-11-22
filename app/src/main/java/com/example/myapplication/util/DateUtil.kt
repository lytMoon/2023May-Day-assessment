package com.example.myapplication.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    fun getDateTitle(): String {
        val currentDate = Date()//获取时间
        val dateFormat = SimpleDateFormat("M月dd日", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}