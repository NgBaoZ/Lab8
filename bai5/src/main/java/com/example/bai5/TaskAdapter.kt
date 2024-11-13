package com.example.bai5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val deleteTask: (Int) -> Unit,
    private val editTask: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var selectedPosition: Int = -1  // Vị trí của task đang được chọn

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)  // Sửa thành ImageButton
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)      // Sửa thành ImageButton

        init {
            itemView.setOnClickListener {
                // Đặt vị trí của tác vụ đang chọn và hiển thị các nút
                selectedPosition = adapterPosition
                notifyDataSetChanged()  // Cập nhật để hiện các nút
            }
            btnEdit.setOnClickListener { editTask(adapterPosition) }
            btnDelete.setOnClickListener { deleteTask(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskDescription.text = task.description

        // Chỉ hiển thị các nút "Xóa" và "Chỉnh sửa" nếu tác vụ được chọn
        if (position == selectedPosition) {
            holder.btnEdit.visibility = View.VISIBLE
            holder.btnDelete.visibility = View.VISIBLE
        } else {
            holder.btnEdit.visibility = View.GONE
            holder.btnDelete.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = tasks.size
}
