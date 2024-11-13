package com.example.bai5

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var etTask: EditText
    private lateinit var btnAddTask: Button
    private lateinit var btnEditTask: Button
    private lateinit var btnCancelEdit: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList = mutableListOf<Task>()
    private var editTaskPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTask = findViewById(R.id.etTask)
        btnAddTask = findViewById(R.id.btnAddTask)
        btnEditTask = findViewById(R.id.btnEditTask)
        btnCancelEdit = findViewById(R.id.btnCancelEdit)
        recyclerView = findViewById(R.id.recyclerView)

        loadTasks()

        taskAdapter = TaskAdapter(taskList, this::deleteTask, this::editTask)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        btnAddTask.setOnClickListener {
            val taskDescription = etTask.text.toString()
            if (taskDescription.isNotBlank()) {
                taskList.add(Task(taskDescription))
                taskAdapter.notifyItemInserted(taskList.size - 1)
                saveTasks()
                etTask.text.clear()
            } else {
                Toast.makeText(this, "Vui lòng nhập tác vụ", Toast.LENGTH_SHORT).show()
            }
        }

        btnEditTask.setOnClickListener {
            val taskDescription = etTask.text.toString()
            if (taskDescription.isNotBlank() && editTaskPosition != -1) {
                taskList[editTaskPosition].description = taskDescription
                taskAdapter.notifyItemChanged(editTaskPosition)
                saveTasks()
                etTask.text.clear()
                resetEditMode()
            } else {
                Toast.makeText(this, "Vui lòng nhập tác vụ", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancelEdit.setOnClickListener { resetEditMode() }
    }

    private fun deleteTask(position: Int) {
        taskList.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
        taskAdapter.notifyItemRangeChanged(position, taskList.size)
        saveTasks()
        resetEditMode()
    }

    private fun editTask(position: Int) {
        etTask.setText(taskList[position].description)
        editTaskPosition = position
        btnAddTask.visibility = View.GONE
        btnEditTask.visibility = View.VISIBLE
        btnCancelEdit.visibility = View.VISIBLE
    }

    private fun resetEditMode() {
        editTaskPosition = -1
        etTask.text.clear()
        btnAddTask.visibility = View.VISIBLE
        btnEditTask.visibility = View.GONE
        btnCancelEdit.visibility = View.GONE
    }

    private fun saveTasks() {
        val sharedPreferences = getSharedPreferences("TaskPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(taskList)
        editor.putString("taskList", json)
        editor.apply()
    }

    private fun loadTasks() {
        val sharedPreferences = getSharedPreferences("TaskPreferences", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("taskList", null)
        val type = object : TypeToken<MutableList<Task>>() {}.type
        json?.let { taskList = Gson().fromJson(it, type) }
    }
}
