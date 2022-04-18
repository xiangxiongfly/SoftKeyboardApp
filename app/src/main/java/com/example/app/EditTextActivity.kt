package com.example.app

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditTextActivity : AppCompatActivity() {
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_text)
        initView()

    }

    private fun initView() {
        // 点击外部隐藏软键盘，提升用户体验
        findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT).setOnClickListener {
            // 隐藏软键，避免内存泄漏
            KeyboardUtils.hideKeyboard(currentFocus)
        }


        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)

        //EditText1获取焦点
        btn1.setOnClickListener {
            editText1.isFocusable = true;
            editText1.isFocusableInTouchMode = true;
            editText1.requestFocus()
        }

        //EditText1失去焦点
        btn2.setOnClickListener {
            editText1.clearFocus()
        }

        //EditText2获取焦点并弹出键盘
        btn3.setOnClickListener {
            editText2.isFocusable = true;
            editText2.isFocusableInTouchMode = true;
            editText2.requestFocus()
            KeyboardUtils.showKeyboard(editText2)
        }

//        //弹出软键盘
        btn4.setOnClickListener {
            KeyboardUtils.showKeyboard(editText1)
        }
//
//        //隐藏软键盘
        btn5.setOnClickListener {
            if (currentFocus != null)
                KeyboardUtils.hideKeyboard(currentFocus)
        }
    }
}