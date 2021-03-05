package com.base.myapplication

import android.os.Bundle
import android.view.View
import com.base.myapplication.base.BaseActivity

class MainActivity : BaseActivity(R.layout.activity_main), MainView {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MainPresenter(this)
        presenter.onCreate()
    }

    override fun onClick(v: View?) {

    }

    override fun initView() {
    }

    override fun initListener() {
    }

}
