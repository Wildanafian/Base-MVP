package com.base.myapplication.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.myapplication.R
import com.base.myapplication.base.BaseFragment

class MainFragment:BaseFragment(R.layout.activity_main,""), MainFragView{

    private lateinit var presenter: MainFragPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = MainFragPresenter(this)
        presenter.onCreateView(mView)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView(v: View) {

    }

    override fun initListener(v: View) {

    }

    override fun onClick(v: View?) {

    }
}