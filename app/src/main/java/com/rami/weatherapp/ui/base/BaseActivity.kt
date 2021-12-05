package com.rami.weatherapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rami.weatherapp.ui.custom.ProgressDialog

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null
    lateinit var binding: T
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        setup(savedInstanceState)
    }

    abstract fun setup(savedInstanceState: Bundle?)
}