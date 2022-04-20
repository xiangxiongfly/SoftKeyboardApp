package com.example.app

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class LoginActivity : AppCompatActivity(), View.OnLayoutChangeListener {
    private lateinit var scrollView: NestedScrollView
    private lateinit var helper: LinearLayout
    private lateinit var content: LinearLayout
    private lateinit var logo: ImageView
    private lateinit var confirm: Button

    private var screenHeight = 0 //屏幕高度
    private var keyboardHeight = 0 //键盘高度

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        scrollView = findViewById(R.id.scrollView)
        helper = findViewById(R.id.helper)
        content = findViewById(R.id.content)
        logo = findViewById(R.id.logo)
        confirm = findViewById(R.id.confirm)

        confirm.setOnClickListener { KeyboardUtils.hideKeyboard(confirm) }

        screenHeight = Resources.getSystem().displayMetrics.heightPixels
        keyboardHeight = screenHeight / 3

        //禁止ScrollView滚动
        scrollView.setOnTouchListener { v, event -> true }

        //监听
        scrollView.addOnLayoutChangeListener(this)
    }

    override fun onLayoutChange(
        v: View?,
        left: Int, top: Int, right: Int, bottom: Int,
        oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
    ) {
        if (oldBottom == 0 || bottom == 0)
            return

        if ((oldBottom - bottom) > keyboardHeight) {
            Log.e("TAG", "软键盘弹出")
            val diff = content.bottom - bottom
            if (diff > 0) {
                AnimatorUtils.translationY(content, 0F, (-diff).toFloat())
                AnimatorUtils.scaleXY(logo, 1F, 0.6F)
                AnimatorUtils.translationY(logo, 0F, -100F)
            }
            helper.visibility = View.INVISIBLE


            Log.e(
                "TAG", """
                (decorView.getHeight()) ${scrollView.rootView.height} 
                (contentView.getHeight()) ${(findViewById<View>(Window.ID_ANDROID_CONTENT)).height}
            """.trimIndent()
            )

        } else if ((bottom - oldBottom) > keyboardHeight) {
            Log.e("TAG", "软键盘收起")
            val diff = content.bottom - oldBottom
            if (diff > 0) {
                AnimatorUtils.translationY(content, content.translationY, 0F)
                AnimatorUtils.scaleXY(logo, 0.6F, 1F)
                AnimatorUtils.translationY(logo, logo.translationY, 0F)
            }
            helper.visibility = View.VISIBLE

            Log.e(
                "TAG", """
                (decorView.getHeight()) ${scrollView.rootView.height} 
                (contentView.getHeight()) ${(findViewById<View>(Window.ID_ANDROID_CONTENT)).height}
            """.trimIndent()
            )

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scrollView.removeOnLayoutChangeListener(this)
    }
}
