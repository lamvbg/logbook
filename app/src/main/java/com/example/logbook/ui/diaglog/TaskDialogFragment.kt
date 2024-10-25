package com.example.logbook.ui.diaglog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.example.logbook.R
import com.example.logbook.ui.models.Task
import java.util.*

class TaskDialogFragment : DialogFragment() {

    private lateinit var taskInput: EditText
    private lateinit var timeInput: EditText
    private lateinit var radioToday: RadioButton
    private lateinit var radioTomorrow: RadioButton
    private var onTaskAddedListener: ((Task, Boolean) -> Unit)? = null // Listener để thêm task
    private var existingTask: Task? = null  // Task đang chỉnh sửa

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_task, null)

        taskInput = view.findViewById(R.id.task_input)
        timeInput = view.findViewById(R.id.time_input)
        radioToday = view.findViewById(R.id.radio_today)
        radioTomorrow = view.findViewById(R.id.radio_tomorrow)

        // Kiểm tra xem có task truyền vào để chỉnh sửa không
        existingTask?.let {
            taskInput.setText(it.task)
            timeInput.setText(it.time)
            if (it.isTodayTask()) {
                radioToday.isChecked = true
            } else if (it.isTomorrowTask()) {
                radioTomorrow.isChecked = true
            }
        }

        builder.setView(view)
            .setTitle(if (existingTask == null) "Add Task" else "Edit Task") // Đổi tiêu đề
            .setPositiveButton(if (existingTask == null) "Add" else "Update") { _, _ ->
                val taskText = taskInput.text.toString()
                val timeText = timeInput.text.toString()
                val isToday = radioToday.isChecked

                if (taskText.isNotEmpty()) {
                    val calendar = Calendar.getInstance()

                    if (!isToday) {
                        calendar.add(Calendar.DAY_OF_YEAR, 1)
                    }
                    val taskDate = calendar.timeInMillis

                    val task = Task(taskText, existingTask?.id ?: 0, existingTask?.status ?: 0, existingTask?.isCompleted ?: false, taskDate, timeText)
                    onTaskAddedListener?.invoke(task, isToday)
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        return builder.create()
    }

    // Hàm để set task hiện tại khi chỉnh sửa
    fun setTask(task: Task) {
        existingTask = task
    }

    // Hàm set listener để callback khi thêm task
    fun setOnTaskAddedListener(listener: (Task, Boolean) -> Unit) {
        this.onTaskAddedListener = listener
    }
}
