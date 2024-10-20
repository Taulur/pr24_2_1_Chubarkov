package com.example.fragmvar3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date

class TaskAdapter(
    private val tasks: List<Task>,
    private val onEditClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.task_title)
        val description: TextView = view.findViewById(R.id.task_description)
        val date:TextView = view.findViewById(R.id.task_date)
        val editButton: Button = view.findViewById(R.id.edit_button)
        val moreButton: Button = view.findViewById(R.id.more_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    private fun moreInformation(view : View) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val snackbar = Snackbar.make(view, "Тут явно что-то будет...", Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(android.graphics.Color.RED)
        snackbar.show()

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.description.text = task.description
        holder.date.text = task.date

        holder.editButton.setOnClickListener {
            onEditClick(task) // Передаем объект Task
        }
        holder.moreButton.setOnClickListener {
            moreInformation(it)
        }
    }


    override fun getItemCount(): Int {
        return tasks.size
    }
}