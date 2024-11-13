package com.example.bai3

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvOpenCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khai báo TextView
        tvOpenCount = findViewById(R.id.tvOpenCount)

        // Khởi tạo SharedPreferences
        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Lấy số lần mở hiện tại từ SharedPreferences
        var openCount = sharedPreferences.getInt("openCount", 0)

        // Tăng số lần mở lên 1
        openCount += 1

        // Cập nhật số lần mở mới vào SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putInt("openCount", openCount)
        editor.apply()

        // Hiển thị số lần mở trên TextView
        tvOpenCount.text = "Bạn đã mở ứng dụng $openCount lần"
    }
}
