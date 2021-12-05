package com.rami.weatherapp.utils.extentions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.rami.weatherapp.ui.base.BaseActivity
import com.rami.weatherapp.ui.custom.ProgressDialog

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
fun BaseActivity<*>.showLoading() {
    try {
        if (progressDialog == null) {
            this.progressDialog = ProgressDialog(this)
        }
        this.progressDialog?.show()
    } catch (e: java.lang.Exception) {
    }
}

fun BaseActivity<*>.hideLoading() {
    if (progressDialog?.isShowing == true) {
        this.progressDialog?.dismiss()
    }
}

fun Activity.hideKeyboard(view: View) {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    view.clearFocus()
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}