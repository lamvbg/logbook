package com.example.logbook.ui.home

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logbook.R
import com.example.logbook.ui.adapters.TaskAdapter
import com.example.logbook.ui.diaglog.TaskDialogFragment
import com.example.logbook.ui.models.DatabaseHandler
import com.example.logbook.ui.models.Task

class HomeFragment : Fragment() {

    private lateinit var recyclerViewToday: RecyclerView
    private lateinit var recyclerViewTomorrow: RecyclerView
    private lateinit var taskAdapterToday: TaskAdapter
    private lateinit var taskAdapterTomorrow: TaskAdapter
    private lateinit var db: DatabaseHandler
    private lateinit var addTaskButton: ImageButton
    private var showCompletedTasks = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Ánh xạ các view từ layout
        recyclerViewToday = view.findViewById(R.id.task_recycler_view_today)
        recyclerViewTomorrow = view.findViewById(R.id.task_recycler_view_tomorrow)
        addTaskButton = view.findViewById(R.id.add_task_button)

        // Khởi tạo database handler và mở database
        db = DatabaseHandler(requireContext())
        db.openDatabase()

        // Thiết lập adapter và recycler view
        setupAdapters()

        // Tải danh sách tasks
        loadTasks()

        // Sự kiện khi nhấn nút thêm task
        addTaskButton.setOnClickListener {
            showAddTaskDialog() // Hiển thị form để thêm task mới
        }

        return view
    }

    private fun setupAdapters() {
        // Khởi tạo adapter cho hôm nay và ngày mai, truyền vào callback cho edit và remove
        taskAdapterToday = TaskAdapter(requireContext(), db, showCompletedTasks, ::editTask, ::removeTask)
        taskAdapterTomorrow = TaskAdapter(requireContext(), db, showCompletedTasks, ::editTask, ::removeTask)

        // Thiết lập layout cho RecyclerView
        recyclerViewToday.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewTomorrow.layoutManager = LinearLayoutManager(requireContext())

        // Gán adapter cho RecyclerView
        recyclerViewToday.adapter = taskAdapterToday
        recyclerViewTomorrow.adapter = taskAdapterTomorrow
    }

    // Load tất cả tasks và chia thành hôm nay và ngày mai
    private fun loadTasks() {
        val taskList = db.getAllTasks()
        val todayTasks = taskList.filter { it.isTodayTask() } // Lọc các task cho hôm nay
        val tomorrowTasks = taskList.filter { it.isTomorrowTask() } // Lọc các task cho ngày mai

        taskAdapterToday.setTasks(todayTasks)
        taskAdapterTomorrow.setTasks(tomorrowTasks)
    }

    // Hiển thị dialog để thêm task mới
    private fun showAddTaskDialog() {
        val dialog = TaskDialogFragment()
        dialog.setOnTaskAddedListener { task, _ ->
            db.insertTask(task) // Thêm task vào database
            loadTasks() // Cập nhật lại danh sách
        }
        dialog.show(parentFragmentManager, "AddTaskDialog")
    }

    // Hiển thị dialog để chỉnh sửa task hiện tại
    private fun editTask(task: Task) {
        val dialog = TaskDialogFragment()
        dialog.setTask(task) // Truyền task cần chỉnh sửa vào dialog
        dialog.setOnTaskAddedListener { updatedTask, _ ->
            updatedTask.id = task.id // Giữ nguyên ID của task cũ
            db.updateTask(task.id, updatedTask.task, updatedTask.date, updatedTask.time) // Cập nhật task trong database
            loadTasks() // Cập nhật lại danh sách
        }
        dialog.show(parentFragmentManager, "EditTaskDialog")
    }

    // Xóa task khỏi danh sách và database
    private fun removeTask(task: Task) {
        db.deleteTask(task.id) // Xóa task từ database
        loadTasks() // Cập nhật lại danh sách sau khi xóa
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu, menu) // Inflate menu từ file XML
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Xử lý sự kiện khi chọn menu "Hide Completed"
        if (item.itemId == R.id.menu_hide_completed) {
            showCompletedTasks = !showCompletedTasks // Đảo trạng thái ẩn/hiện task hoàn thành
            item.title = if (showCompletedTasks) "Hide Completed" else "Show Completed"
            taskAdapterToday.setShowCompletedTasks(showCompletedTasks)
            taskAdapterTomorrow.setShowCompletedTasks(showCompletedTasks)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Thông báo có menu cho fragment
    }
}
