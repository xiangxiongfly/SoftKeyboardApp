package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView

class ScrollViewActivity : AppCompatActivity() {
    private lateinit var scrollView: ScrollView
    private lateinit var content: LinearLayout
    private var scrollHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view)
        scrollView = findViewById(R.id.scrollView)
        content = findViewById(R.id.content)
        content.post { scrollHeight = content.measuredHeight }
        SoftKeyboardListener.with(this)
            .registerListener(object : SoftKeyboardListener.OnSoftKeyboardListener {
                override fun onKeyboardShow(keyboardHeight: Int) {
                    scrollView.smoothScrollTo(0, scrollHeight)
                }

                override fun onKeyboardHide() {

                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        SoftKeyboardListener.with(this).unregisterListener()
    }
}