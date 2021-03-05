package com.base.myapplication.base

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast

abstract class BaseDialog(open val activity: Activity, val layout: Int) : Dialog(activity),
    BaseDialogView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setContentView(layout)

        initView()
        initListener()
    }

    fun myToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}