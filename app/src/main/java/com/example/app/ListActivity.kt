package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView


class ListActivity : AppCompatActivity() {
    lateinit var listView: ListView
    lateinit var bottomNav: TextView
    lateinit var rootView: LinearLayout
    lateinit var scrollView: NestedScrollView
    lateinit var etFooter: EditText
    lateinit var etHeader: EditText
    lateinit var btn: Button
    var flag = 0
    var isShowKeyboard = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        listView = findViewById(R.id.listView)
        bottomNav = findViewById(R.id.bottomNav)
        rootView = findViewById(R.id.rootView)
        scrollView = findViewById(R.id.scrollView)

        val footerView = LayoutInflater.from(this).inflate(R.layout.layout_footer, null)

        etHeader = findViewById(R.id.etHeader)
        etFooter = footerView.findViewById(R.id.etFooter)
        btn = footerView.findViewById(R.id.btn)

        listView.addFooterView(footerView)

        val array = Array(30) { it.toString() }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)

        SoftKeyboardListener.with(this)
            .registerListener(object : SoftKeyboardListener.OnSoftKeyboardListener {
                override fun onKeyboardShow(keyboardHeight: Int) {
                    Log.e("TAG", "onKeyboardShow")
                    isShowKeyboard = true
                }

                override fun onKeyboardHide() {
                    Log.e("TAG", "onKeyboardHide")
                    isShowKeyboard = false
                    bottomNav.visibility = VISIBLE
                }
            })

        btn.setOnClickListener {
            KeyboardUtils.hideKeyboard(btn)
        }
        etHeader.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Log.e("TAG", "header down")
                flag = 1
                bottomNav.visibility = GONE
                etHeader.isFocusable = true
                etHeader.isFocusableInTouchMode = true;
                etHeader.requestFocus()
                KeyboardUtils.forceShowKeyboard(etHeader)
            }
            false
        }


        etFooter.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Log.e("TAG", "footer down")
                flag = 2
                bottomNav.visibility = GONE
                etFooter.isFocusable = true
                etFooter.isFocusableInTouchMode = true
                etFooter.requestFocus()
                KeyboardUtils.forceShowKeyboard(etFooter)
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoftKeyboardListener.with(this).unregisterListener()
    }
}