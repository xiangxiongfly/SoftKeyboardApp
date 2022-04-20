package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.app.SoftKeyboardListener.OnSoftKeyboardListener

class LoginActivity2 : AppCompatActivity() {
    private lateinit var content: LinearLayout
    private lateinit var help: View
    private lateinit var confirm: Button
    private lateinit var logo: ImageView
    private var diff = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        content = findViewById(R.id.content)
        confirm = findViewById(R.id.confirm)
        help = findViewById(R.id.help)
        logo = findViewById(R.id.logo)

        help.post {
            diff = help.bottom - confirm.top
        }

        SoftKeyboardListener.with(this).registerListener(object : OnSoftKeyboardListener {
            override fun onKeyboardShow(keyboardHeight: Int) {
                Log.e("TAG", "软键盘弹出")
                AnimatorUtils.scaleXY(logo, 1F, 0.6F)
                AnimatorUtils.translationY(logo, 0F, -100F)
//                content.scrollBy(0, diff)
                AnimatorUtils.translationY(content, 0F, -diff.toFloat())
            }

            override fun onKeyboardHide() {
                Log.e("TAG", "软键盘收起")
                AnimatorUtils.scaleXY(logo, 0.6F, 1F)
                AnimatorUtils.translationY(logo, logo.translationY, 0F)
//                content.scrollTo(0, 0)
                AnimatorUtils.translationY(content, -diff.toFloat(), 0F)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        SoftKeyboardListener.with(this).unregisterListener()
    }
}