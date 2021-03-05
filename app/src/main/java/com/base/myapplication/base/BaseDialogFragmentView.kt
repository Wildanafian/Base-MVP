package com.base.myapplication.base

import android.view.View

interface BaseDialogFragmentView {
    fun initView(v: View)
    fun initListener(v: View)
}