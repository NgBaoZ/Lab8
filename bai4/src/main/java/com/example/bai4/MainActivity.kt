package com.example.bai4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogout = findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener {
            // Xóa trạng thái đăng nhập
            val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            // Chuyển về LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
