package com.example.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.app.SoftKeyboardListener.OnSoftKeyboardListener

class LoginActivity2 : AppCompatActivity() {
    private lateinit var rootView: RelativeLayout
    private lateinit var help: View
    private lateinit var confirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        rootView = findViewById(R.id.rootView)
        help = findViewById(R.id.help)
        confirm = findViewById(R.id.confirm)

        SoftKeyboardListener.registerListener(this, object : OnSoftKeyboardListener {
            override fun onKeyboardShow(keyboardHeight: Int, diff: Int) {
                rootView.scrollTo(0, help.bottom - diff)
            }

            override fun onKeyboardHide(keyboardHeight: Int) {
                rootView.scrollTo(0, 0)
            }
        })
    }
}