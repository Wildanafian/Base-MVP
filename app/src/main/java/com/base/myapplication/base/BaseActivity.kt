package com.base.myapplication.base

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.crowdfire.cfalertdialog.CFAlertDialog
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.base.myapplication.R
import com.base.myapplication.helper.hideLoading
import com.base.myapplication.helper.showLoading

abstract class BaseActivity(layout : Int) : AppCompatActivity(layout), View.OnClickListener,
    BaseActivityView{

    private var toolbar: Toolbar? = null
    private var backButtonHasBeenClickedOnce = false
    var mLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        mLayoutManager = LinearLayoutManager(this)
    }

    override fun setContentView(view: View?, layoutParams: ViewGroup.LayoutParams?) {
        super.setContentView(view, layoutParams)
        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            toolbar!!.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
            toolbar!!.title = ""
        }
    }

    fun setDisplayHome(showBackButton: Boolean) {
        val actionBar = supportActionBar ?: return
        actionBar.setHomeButtonEnabled(showBackButton)
        actionBar.setDisplayHomeAsUpEnabled(showBackButton)
    }

    fun setHomeAsUpIndicator(@DrawableRes drawable: Int) {
        val actionBar = supportActionBar ?: return
        actionBar.setHomeAsUpIndicator(drawable)
    }

    fun setDisplayLogo(@DrawableRes drawable: Int) {
        val actionBar = supportActionBar ?: return
        actionBar.setLogo(drawable)
    }

    fun setIcon(@DrawableRes drawable: Int) {
        val actionBar = supportActionBar ?: return
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setIcon(ContextCompat.getDrawable(this, drawable))
        actionBar.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    fun setTitle(title: String?) {
        val toolbarTitle = findViewById<TextView>(R.id.tvToolbarName)
        toolbarTitle!!.typeface = Typeface.createFromAsset(this.assets, "segoe_ui.ttf")
        toolbarTitle.text = title
    }

    fun setSubtitle(subtitle: String?) {
        val actionBar = supportActionBar ?: return
        actionBar.setDisplayShowTitleEnabled(!TextUtils.isEmpty(subtitle))
        actionBar.subtitle = subtitle
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupToolbar()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        setupToolbar()
    }

//    override fun onResume() {
//        super.onResume()
//        networkChecker()
//    }

    override fun startTask() {
        showLoading(this)
    }

    override fun finishedTask() {
        hideLoading()
    }

    override fun failureTask(message: String) {
        showDialog("Gagal", message, "OK")
    }

    override fun failureTask(title: String, message: String) {
        showDialog(title, message, "OK")
    }

    override fun info(message: String) {
        showDialog("Info", message, "OK")
    }

    private fun showDialog(title: String, message: String, button: String) {
        val builder = CFAlertDialog.Builder(this)
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
        builder.addButton(
            button,
            Color.parseColor("#ffffff"),
            Color.parseColor("#429ef4"),
            CFAlertDialog.CFAlertActionStyle.POSITIVE,
            CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
        ) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

//    private fun networkChecker() {
//        if (!networkCheck(this))
//            showDialog("Info", "Tidak ada koneksi, periksa koneksi anda!", "OK")
//    }

    protected fun closeWhenBackPressedTwice() {
        if (backButtonHasBeenClickedOnce) { finish()
            return
        }
        backButtonHasBeenClickedOnce = true
        Toast.makeText(this, "Tekan tombol kembali dua kali untuk keluar", Toast.LENGTH_SHORT).show()
        Handler()
            .postDelayed({ backButtonHasBeenClickedOnce = false }, 2000)
    }

    protected fun myToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun checkUpdate(){
        val updateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = updateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                updateManager.startUpdateFlowForResult(appUpdateInfo,
                    AppUpdateType.IMMEDIATE, this, 12)
            }
        }
    }
}