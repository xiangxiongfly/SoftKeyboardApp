package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.lang.Math.abs

class ChatActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var etInput: EditText
    private lateinit var btnSend: Button
    private lateinit var btnPanel: Button
    private lateinit var tvPanel: TextView
    private lateinit var bar: LinearLayout

    private var isShowPanel = false
    private var isShowKeyboard = false

    private var mKeyboardHeight = 0
    private var mPanelHeight = 0
    private val FLAG_PANEL = 1
    private val FLAG_INPUT = 2
    private var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initView()
        initData()

        tvPanel.post {
            mPanelHeight = tvPanel.measuredHeight
        }

        SoftKeyboardListener.with(this)
            .registerListener(object : SoftKeyboardListener.OnSoftKeyboardListener {
                override fun onKeyboardShow(keyboardHeight: Int) {
                    isShowKeyboard = true
                    listView.setSelection(listView.count)
                    mKeyboardHeight = keyboardHeight
                    if (flag == FLAG_INPUT) {
                        bar.scrollTo(0, 0)
                    }
                }

                override fun onKeyboardHide() {
                    isShowKeyboard = false
                    if (flag == FLAG_PANEL) {
                        bar.scrollTo(0, 0)
                    }
                }
            })

        etInput.setOnTouchListener { v, event ->
            flag = FLAG_INPUT
            if (isShowPanel) {
                bar.scrollBy(0, mKeyboardHeight)
                showPanel(false)
            }
            etInput.requestFocus()
            KeyboardUtils.showKeyboard(etInput)
            false
        }

        btnSend.setOnClickListener {
            KeyboardUtils.hideKeyboard(btnSend)
            showPanel(false)
        }

        btnPanel.setOnClickListener {
            flag = FLAG_PANEL
            if (isShowKeyboard) {
                bar.scrollBy(0, mKeyboardHeight)
                KeyboardUtils.hideKeyboard(btnPanel)
            }
            if (isShowPanel)
                showPanel(false)
            else
                showPanel(true)
        }
    }

    private fun initData() {
        val datas = Array(30) { it.toString() }
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas)
    }

    private fun initView() {
        listView = findViewById(R.id.listView)
        etInput = findViewById(R.id.etInput)
        btnSend = findViewById(R.id.btnSend)
        btnPanel = findViewById(R.id.btnPanel)
        tvPanel = findViewById(R.id.tvPanel)
        bar = findViewById(R.id.bar)
    }

    private fun showPanel(show: Boolean) {
        isShowPanel = show
        if (isShowPanel)
            tvPanel.visibility = View.VISIBLE
        else
            tvPanel.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        SoftKeyboardListener.with(this).unregisterListener()
    }
}