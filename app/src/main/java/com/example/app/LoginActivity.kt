package com.example.app

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView


/*
思路：
软键盘弹起时：会将Window向上顶起，这时NestedScrollView的oldBottom会大于bottom，需要滚动(oldBottom-bottom)的距离。
软键盘收起时：Window恢复原样，这时NestedScrollView的oldBottom会小于bottom，需要滚动(bottom-oldBottom)的距离。
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var scrollView: NestedScrollView
    private lateinit var helper: LinearLayout
    private lateinit var wrapper: LinearLayout
    private lateinit var logo: ImageView
    private lateinit var confirm: Button

    private var screenHeight = 0 //屏幕高度
    private var keyboardHeight = 0 //键盘高度

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        BarUtils.setTransparentStatusBar(this)
        scrollView = findViewById(R.id.scrollView)
        helper = findViewById(R.id.helper)
        wrapper = findViewById(R.id.wrapper)
        logo = findViewById(R.id.logo)
        confirm = findViewById(R.id.confirm)

        confirm.setOnClickListener { KeyboardUtils.hideKeyboard(confirm) }

        screenHeight = Resources.getSystem().displayMetrics.heightPixels
        keyboardHeight = screenHeight / 3

        scrollView.setOnTouchListener { v, event -> true }

        scrollView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (oldBottom != 0 && bottom != 0) {
                if ((oldBottom - bottom) > keyboardHeight) {
                    //软键盘展开
                    val diff = wrapper.bottom - bottom
                    if (diff > 0) {
                        //比较生硬
//                        scrollView.smoothScrollBy(0, diff)
                        AnimatorUtils.translationY(wrapper, 0F, (-diff).toFloat())
                        AnimatorUtils.scale(logo, 1F, 0.6F)
                    }
                    helper.visibility = View.INVISIBLE
                } else if ((bottom - oldBottom) > keyboardHeight) {
                    //软键盘收起
                    val diff = wrapper.bottom - oldBottom
                    if (diff > 0) {
                        //比较生硬
//                        scrollView.smoothScrollBy(0, -diff)
                        AnimatorUtils.translationY(wrapper, wrapper.translationY, 0F)
                        AnimatorUtils.scale(logo, 0.6F, 1F)
                    }
                    helper.visibility = View.VISIBLE
                }
            }
        }
    }
}
