package com.example.logbook.ui.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.logbook.R
import com.example.logbook.ui.models.DatabaseHandler
import com.example.logbook.ui.models.Task

class TaskAdapter(
    private val context: Context,
    private val db: DatabaseHandler,
    private var showCompletedTasks: Boolean,
    private val onTaskEdit: (Task) -> Unit,
    private val onTaskRemove: (Task) -> Unit
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private var taskList: List<Task> = ArrayList()

    fun setTasks(taskList: List<Task>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }

    fun setShowCompletedTasks(show: Boolean) {
        this.showCompletedTasks = show
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskName.text = task.task
        holder.taskTime.text = task.time

        holder.btnEdit.setOnClickListener {
            onTaskEdit(task) // Gọi callback edit
        }

        // Sự kiện cho nút Remove
        holder.btnRemove.setOnClickListener {
            onTaskRemove(task) // Gọi callback remove
        }

        if (task.isTomorrowTask) {
            holder.taskCompleted.visibility = View.GONE
            holder.dotIcon.visibility = View.VISIBLE

            holder.itemView.isEnabled = false
        } else {
            holder.taskCompleted.visibility = View.VISIBLE
            holder.dotIcon.visibility = View.GONE
            holder.taskCompleted.isChecked = task.isCompleted

            holder.taskCompleted.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                task.isCompleted =
                    isChecked
                db.updateStatus(task.id, if (isChecked) 1 else 0)
                notifyDataSetChanged()
            }

            if (task.isCompleted) {
                holder.taskName.paintFlags = holder.taskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.taskName.setTextColor(ContextCompat.getColor(context, R.color.gray))
            } else {
                holder.taskName.paintFlags = holder.taskName.paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG.inv())
                holder.taskName.setTextColor(ContextCompat.getColor(context, android.R.color.black))
            }
        }

        holder.itemView.visibility = if (task.isCompleted && !showCompletedTasks) View.GONE else View.VISIBLE
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskName: TextView = itemView.findViewById<TextView>(R.id.task_name)
        var taskTime: TextView = itemView.findViewById<TextView>(R.id.task_time)
        var taskCompleted: CheckBox = itemView.findViewById<CheckBox>(R.id.task_completed)
        var dotIcon: ImageView = itemView.findViewById<ImageView>(R.id.dot_icon)
        var btnEdit: ImageButton = itemView.findViewById(R.id.btn_edit)
        var btnRemove: ImageButton = itemView.findViewById(R.id.btn_remove)
    }
}