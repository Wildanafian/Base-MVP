package com.base.myapplication.base

import android.view.View

interface BaseFragmentView {
    fun initView(v: View)
    fun initListener(v: View)
    fun startTask()
    fun finishedTask()
    fun failureTask(message: String, errorCode: String?)
    fun info(message: String)
}