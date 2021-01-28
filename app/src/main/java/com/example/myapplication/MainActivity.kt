package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.cpu.NativeCPUAdActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_test).setOnClickListener{
            startActivity(Intent(this,AwardNewsNativeActivity::class.java))
        }
        findViewById<TextView>(R.id.test_2).setOnClickListener{
            startActivity(Intent(this,NativeCPUAdActivity::class.java))

        }
    }
}