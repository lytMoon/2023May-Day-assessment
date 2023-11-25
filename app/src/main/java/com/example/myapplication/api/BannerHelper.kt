package com.example.myapplication.api

import com.example.myapplication.adapter.NewsAdapter

interface BannerHelper {

    fun initBanner(rv: NewsAdapter)
    fun startRun()
    fun destroyRun()

}
