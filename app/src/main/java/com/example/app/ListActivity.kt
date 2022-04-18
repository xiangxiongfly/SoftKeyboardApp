package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView


class ListActivity : AppCompatActivity() {
    lateinit var listView: ListView
    lateinit var bottomNav: TextView
    lateinit var rootView: LinearLayout
    lateinit var scrollView: NestedScrollView
    lateinit var editText: EditText
    lateinit var editText2: EditText
    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        listView = findViewById(R.id.listView)
        bottomNav = findViewById(R.id.bottomNav)
        rootView = findViewById(R.id.rootView)
        scrollView = findViewById(R.id.scrollView)

        val footerView = LayoutInflater.from(this).inflate(R.layout.layout_footer, null)
        val headerView = LayoutInflater.from(this).inflate(R.layout.layout_header, null)

        editText = footerView.findViewById(R.id.editText)
        editText2 = headerView.findViewById(R.id.editText2)
        btn = footerView.findViewById(R.id.btn)
        btn.setOnClickListener {
//            editText.clearFocus()
//            KeyboardUtils.hideKeyboard(currentFocus)
//            object : Thread() {
//                override fun run() {
//                    val instrumentation = Instrumentation()
//                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK)
//                }
//            }.start()


//            val view: View? = currentFocus
//            if (view != null) {
//                (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
//                    view.getWindowToken(),
//                    0
//                )
//            }

            editText2.postDelayed({ KeyboardUtils.toggleSoftInput(editText) }, 500)


        }

        listView.addFooterView(footerView)
        listView.addHeaderView(headerView)

        val array = Array(100) { it.toString() }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)

        SoftKeyboardListener.registerListener(
            this,
            object : SoftKeyboardListener.OnSoftKeyboardListener {
                override fun onKeyboardShow(keyboardHeight: Int, diff: Int) {
                }

                override fun onKeyboardHide(keyboardHeight: Int) {
                    Log.e("TAG", "hideKeyboard")
                    bottomNav.visibility = VISIBLE
                }
            })

        editText2.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                bottomNav.visibility = GONE
                editText2.requestFocus()
                KeyboardUtils.showKeyboard(editText2)
            }
            return@setOnTouchListener false
        }
        editText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                bottomNav.visibility = GONE
                editText.requestFocus()
                KeyboardUtils.showKeyboard(editText)
            }
            return@setOnTouchListener false
        }
    }
}