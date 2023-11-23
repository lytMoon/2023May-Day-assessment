package com.example.myapplication.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
object DateUtil {
    private var mDate = LocalDate.now()


    fun getDateTitle(): String {
        val currentDate = Date()//获取时间
        val dateFormat = SimpleDateFormat("M月dd日", Locale.getDefault())
        return dateFormat.format(currentDate)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataBefore(): String {
        // 将日期减去一天
        mDate = mDate.minusDays(1)
        // 将日期格式化为字符串
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return mDate.format(formatter)
    }

}