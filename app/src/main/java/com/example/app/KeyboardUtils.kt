package com.example.app

import android.app.Instrumentation
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 软键盘工具类
 */
object KeyboardUtils {
    /**
     * 显示软键盘
     * 需要先 requestFocus 获取焦点，如果是在 Activity Create，那么需要延迟一段时间
     */
    fun showKeyboard(view: View?) {
        if (view == null)
            return
        val manager: InputMethodManager = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(view, 0)
    }

    /**
     * 强制显示软键盘，页面滚动时不会收起
     */
    fun forceShowKeyboard(view: EditText?) {
        if (view == null)
            return
        val manager: InputMethodManager = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    /**
     * 隐藏软键盘
     */
    fun hideKeyboard(view: View?) {
        if (view == null)
            return
        val manager: InputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 强制隐藏软键盘
     * 谨慎使用，需要判断软键盘是否开启
     */
    fun forceHideKeyboard() {
        object : Thread() {
            override fun run() {
                try {
                    val inst = Instrumentation()
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    /**
     * 切换软键盘
     */
    fun toggleSoftInput(view: View?) {
        if (view == null)
            return
        val manager: InputMethodManager = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.toggleSoftInput(0, 0)
    }
}