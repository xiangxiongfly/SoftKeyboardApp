package com.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.toEditTextActivity).setOnClickListener {
            startActivity(Intent(this, EditTextActivity::class.java))
        }
        findViewById<Button>(R.id.toLoginActivity).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        findViewById<Button>(R.id.toLoginActivity2).setOnClickListener {
            startActivity(Intent(this, LoginActivity2::class.java))
        }
        findViewById<Button>(R.id.toListActivity).setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
}