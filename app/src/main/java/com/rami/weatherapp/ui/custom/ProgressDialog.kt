package com.rami.weatherapp.ui.custom

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import com.rami.weatherapp.R

/**
 * Created by Rami El-bouhi on 30,November,2021
 */
class ProgressDialog(context: AppCompatActivity) : Dialog(context) {

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.progress_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}