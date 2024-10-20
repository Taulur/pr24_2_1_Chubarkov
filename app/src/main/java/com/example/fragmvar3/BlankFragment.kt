package com.example.fragmvar3

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date


class BlankFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Инициализация адаптера
        adapter = TaskAdapter(tasks) { task -> showEditDialog(task) }
        recyclerView.adapter = adapter

        // Загрузка задач из SharedPreferences
        loadTasks()

        return view
    }

    private fun loadTasks() {
        val sharedPreferences = requireContext().getSharedPreferences("tasks", Context.MODE_PRIVATE)
        // Здесь предполагается, что у вас есть уникальные идентификаторы для задач
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        for (i in 1..10) { // Пример загрузки 10 задач
            val title = sharedPreferences.getString("task_${i}_title", "Расходы $i")
            val description = sharedPreferences.getString("task_${i}_description", "Точная информация по расходам $i")
            val date = sharedPreferences.getString("task_${i}_date",currentDate)
            if (title != null && description != null && date != null) {
                tasks.add(Task(i, title, description, date))
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun showEditDialog(task: Task) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_task, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.edit_task_title)
        val descriptionEditText = dialogView.findViewById<EditText>(R.id.edit_task_description)
        val dateEditText = dialogView.findViewById<EditText>(R.id.edit_task_date)

        titleEditText.setText(task.title)
        descriptionEditText.setText(task.description)
        dateEditText.setText(task.date)

        AlertDialog.Builder(requireContext())
            .setTitle("Редактировать расход")
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val newTitle = titleEditText.text.toString()
                val newDescription = descriptionEditText.text.toString()
                val newDate = dateEditText.text.toString()
                updateTask(task, newTitle, newDescription, newDate)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }


    private fun updateTask(task: Task, newTitle: String, newDescription: String, newDate : String) {
        task.title = newTitle
        task.description = newDescription
        task.date = newDate

        // Сохранение изменений в SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("tasks", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("task_${task.id}_title", task.title)
        editor.putString("task_${task.id}_description", task.description)
        editor.putString("task_${task.id}_date", task.date)
        editor.apply()

        // Уведомляем адаптер об изменении конкретного элемента
        val position = tasks.indexOf(task)
        adapter.notifyItemChanged(position)
    }
}