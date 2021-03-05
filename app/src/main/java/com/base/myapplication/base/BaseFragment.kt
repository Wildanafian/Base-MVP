package com.base.myapplication.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crowdfire.cfalertdialog.CFAlertDialog
import com.pos.rewash.helper.*
import com.base.myapplication.helper.*

abstract class BaseFragment(val layout : Int, val screen: String) : Fragment(), BaseFragmentView, View.OnClickListener {

    lateinit var mView: View
    var mLayoutManager: LinearLayoutManager? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mLayoutManager = LinearLayoutManager(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(layout, container, false)
        return mView
    }

    override fun onDetach() {
        super.onDetach()
    }

//    fun networkChecker2(): Boolean {
//        return networkCheck(requireContext())
//    }

    override fun onDestroy() {
        super.onDestroy()
//        CustomLoading.CustomHide()
    }

    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    override fun startTask() {
        showLoading(requireContext())
    }

    override fun finishedTask() {
        hideLoading()
    }

    override fun failureTask(message: String, errorCode: String?) {
        showDialog("Gagal", message, "OK")
    }

    override fun info(message: String) {
        showDialog("Info", message, "OK")
    }

    private fun showDialog(title: String, message: String, button: String) {
        val builder = CFAlertDialog.Builder(requireContext())
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
        builder.addButton(
            button,
            Color.parseColor("#ffffff"),
            Color.parseColor("#42B3FF"),
            CFAlertDialog.CFAlertActionStyle.POSITIVE,
            CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
        ) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    protected fun myToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}