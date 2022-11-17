package com.example.weatherapp.ui.utils

import android.view.View

object ContextExtensions {

    fun View.visible(b: Boolean){
        if (b) this.visibility = View.VISIBLE
        else this.visibility = View.GONE
    }
}