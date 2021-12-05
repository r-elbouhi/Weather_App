package com.rami.weatherapp.ui.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.rami.weatherapp.R
import com.rami.weatherapp.data.model.utils.BaseApiResponse
import com.rami.weatherapp.data.model.utils.CustomError
import com.rami.weatherapp.utils.ApiStatus
import com.rami.weatherapp.utils.extentions.hideLoading
import com.rami.weatherapp.utils.extentions.showLoading

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
abstract class BaseFragment<T : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {

    protected abstract val viewModel: ViewModel
    abstract fun getLayoutId(): Int
    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, getLayoutId(), container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(this, {
            it?.let {
                baseRender(it)
            }
        })
    }

    private fun baseRender(response: BaseApiResponse<Any>) {
        when (response.apiStatus) {
            ApiStatus.LOADING -> {
                showLoader()
            }
            ApiStatus.UN_AUTHORIZED -> {
                hideLoader()
                showToast("Not authorized")
                // open login screen
            }
            else -> {
                showErrorMsg(response.error)
            }
        }
    }

    // not private for the sake of overriding in case of custom implementation for specific screens
    open fun showLoader() {
        showLoading()
    }

    // not private for the sake of overriding in case of custom implementation for specific screens
    open fun hideLoader() {
        hideLoading()
    }

    // not private for the sake of overriding in case of custom implementation for specific screens
    open fun showErrorMsg(customError: CustomError?) {
        hideLoading()
        showToast(customError?.message)
    }

    fun showLoading() {
        (activity as? BaseActivity<*>)?.showLoading()
    }

    fun hideLoading() {
        (activity as? BaseActivity<*>)?.hideLoading()
    }

    // Any to give me ability to pass String or id
    private fun showToast(msg: Any?) {
        context?.let {
            val message = when (msg) {
                null -> {
                    getString(R.string.error_happend)
                }
                is Int -> {
                    getString(msg)
                }
                else -> {
                    msg.toString()
                }
            }
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
}