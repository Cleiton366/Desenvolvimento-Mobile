package com.example.a1_entrega_mobile

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import java.util.*
import kotlin.system.exitProcess

class SubmitTask : AppCompatActivity() {

    private var radioValue : Int = 0
    private var isEditing : Boolean = false
    private var taskList : MutableList<Task> = ArrayList()
    private  var taskPosition : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_task)

        //setting spinner values
        val spinner: Spinner = findViewById(R.id.prioritySpinner)
        ArrayAdapter.createFromResource(this, R.array.taskPriority,
            android.R.layout.simple_spinner_item
        ).also { adapter -> adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter }

        //getting task value when editing task
        val taskObj = intent.extras?.get("taskObj") as? TaskObj
        val newTaskList = intent.extras?.get("taskList") as? MutableList<Task>

        newTaskList ?.let {
            taskList = newTaskList
        }
        taskObj ?.let{
            isEditing = true
            radioValue = taskObj.task.estimative
            taskList = taskObj.taskList
            taskPosition = taskObj.taskPosition
            fillForm(taskObj.task)
        }

        if(!isEditing) {
            val deleteBtn : Button = findViewById(R.id.deleteBtn)
            deleteBtn.setVisibility(View.GONE);
        }

        //submit task btn
        val submitTaskBtn : Button = findViewById(R.id.submitTaskBtn)
        submitTaskBtn.setOnClickListener {
            playSubmitTaskAudio()
            submitTask()
        }

        //cancel btm
        val cancelBtn : Button = findViewById(R.id.cancelBtn)
        cancelBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("taskList", ArrayList(taskList))
            startActivity(intent)
        }

        //delete bnt
        val deleteBtn : Button = findViewById(R.id.deleteBtn)
        deleteBtn.setOnClickListener {
            taskList.removeAt(taskPosition)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("taskList", ArrayList(taskList))
            startActivity(intent)
        }

        //getting ratio values
        val estimateRatio = findViewById<RadioGroup>(R.id.radio_group_estimative)
        estimateRatio.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                radioValue = radio.id
            }
        )
    }

    // render menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    //change activity on the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                Toast.makeText(this, "Primeira entrega de Desenvolvimento de Dispositivos Moveis", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.create_new_task -> {
                true
            }
            R.id.view_tasks -> {
                val intent = Intent(this,  MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.user_profile -> {
                val intent = Intent(this,  UserProfile::class.java)
                startActivity(intent)
                true
            }
            else -> exitProcess(-1)
        }
    }

    //play audios given an url
    private fun playSubmitTaskAudio() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.submittasksound)
        mediaPlayer.start()
    }

    private fun submitTask() {
        val taskName = findViewById<EditText>(R.id.editTextTaskName)
        val taskPriority = findViewById<Spinner>(R.id.prioritySpinner)
        val taskNotification = findViewById<ToggleButton>(R.id.toggleBtnNotification)


        val task = Task(taskNotification.isChecked, taskPriority.selectedItemPosition,
            taskName.text.toString(), radioValue, UUID.randomUUID().toString())

        if(isEditing) {
            taskList[taskPosition].name = task.name
            taskList[taskPosition].notification = task.notification
            taskList[taskPosition].priority = task.priority
            taskList[taskPosition].estimative = task.estimative
        } else {
            taskList.add(task)
        }

        val newTaskList = taskList
        val taskObj = TaskObj(newTaskList, task, taskPosition)

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("taskObj", taskObj)
        startActivity(intent)
    }

    private fun fillForm(task : Task) {

        val taskName = findViewById<EditText>(R.id.editTextTaskName)
        val taskPriority = findViewById<Spinner>(R.id.prioritySpinner)
        val taskNotification = findViewById<ToggleButton>(R.id.toggleBtnNotification)
        val estimateRatio = findViewById<RadioButton>(task.estimative)

        taskName.setText(task.name)
        taskPriority.setSelection(task.priority, false)

        estimateRatio ?.let{
            estimateRatio.setChecked(true)
        }

        if(task.notification) {
            taskNotification.setChecked(true)
        } else taskNotification.setChecked(false)
    }
}