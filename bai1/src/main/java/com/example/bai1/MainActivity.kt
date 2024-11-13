package com.example.bai1

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khai báo các thành phần giao diện
        val editTextData = findViewById<EditText>(R.id.editTextData)
        val btnSaveData = findViewById<Button>(R.id.btnSaveData)
        val btnGetData = findViewById<Button>(R.id.btnGetData)

        // Khởi tạo SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        // Xử lý sự kiện khi nhấn nút "Lưu dữ liệu"
        btnSaveData.setOnClickListener {
            val data = editTextData.text.toString()
            if (data.isNotEmpty()) {
                // Lưu dữ liệu vào SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("savedData", data)
                editor.apply()
                Toast.makeText(this, "Dữ liệu đã được lưu", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý sự kiện khi nhấn nút "Lấy dữ liệu"
        btnGetData.setOnClickListener {
            // Kiểm tra xem dữ liệu có tồn tại trong SharedPreferences không
            val savedData = sharedPreferences.getString("savedData", null)
            if (savedData != null) {
                Toast.makeText(this, "Dữ liệu đã lưu: $savedData", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Không có dữ liệu nào đã lưu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
