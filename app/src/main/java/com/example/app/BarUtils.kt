package com.example.app

import android.app.Activity
import android.view.WindowManager

object BarUtils {
    /**
     * 设置透明状态栏
     */
    fun setTransparentStatusBar(activity: Activity) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //透明状态栏
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) //透明导航栏
    }
}