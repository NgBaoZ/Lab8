package com.example.bai2

import android.content.Context
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {

    private lateinit var switchDarkMode: Switch
    private lateinit var switchFontSize: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khai báo các thành phần giao diện
        switchDarkMode = findViewById(R.id.switchDarkMode)
        switchFontSize = findViewById(R.id.switchFontSize)

        // Khởi tạo SharedPreferences
        val sharedPreferences = getSharedPreferences("UserSettings", Context.MODE_PRIVATE)

        // Khôi phục cài đặt từ SharedPreferences
        val isDarkModeEnabled = sharedPreferences.getBoolean("darkMode", false)
        val isLargeFontEnabled = sharedPreferences.getBoolean("largeFont", false)

        // Áp dụng cài đặt chế độ tối hoặc sáng
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        // Áp dụng cài đặt kích thước chữ
        if (isLargeFontEnabled) {
            switchFontSize.textSize = 24f // Kích thước lớn
        } else {
            switchFontSize.textSize = 16f // Kích thước bình thường
        }

        // Thiết lập trạng thái của các switch
        switchDarkMode.isChecked = isDarkModeEnabled
        switchFontSize.isChecked = isLargeFontEnabled

        // Xử lý sự kiện khi thay đổi chế độ tối
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("darkMode", isChecked)
            editor.apply()

            // Áp dụng chế độ tối hoặc sáng
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // Xử lý sự kiện khi thay đổi kích thước chữ
        switchFontSize.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("largeFont", isChecked)
            editor.apply()

            // Cập nhật kích thước chữ ngay lập tức
            if (isChecked) {
                switchFontSize.textSize = 24f // Kích thước lớn
            } else {
                switchFontSize.textSize = 16f // Kích thước bình thường
            }
        }
    }
}
