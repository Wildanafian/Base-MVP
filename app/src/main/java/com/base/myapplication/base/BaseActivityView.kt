package com.base.myapplication.base

interface BaseActivityView {
    fun initView()
    fun initListener()
    fun startTask()
    fun finishedTask()
    fun failureTask(message: String)
    fun failureTask(title: String, message: String)
    fun info(message: String)
}