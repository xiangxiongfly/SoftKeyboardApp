package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button

class AdjustResizeActivity : AppCompatActivity() {
    private lateinit var contentIdView: View
    private lateinit var decorView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adjust_resize)

        contentIdView = findViewById(Window.ID_ANDROID_CONTENT)
        decorView = contentIdView.rootView

        findViewById<Button>(R.id.btn).setOnClickListener {
            Log.e("TAG","""
                adjustResize:
                contentIdView: ${contentIdView.height}  ${contentIdView.measuredHeight}
                decorView: ${decorView.height}  ${decorView.measuredHeight}
            """.trimIndent())
        }
    }
}