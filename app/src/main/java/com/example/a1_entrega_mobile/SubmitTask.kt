package com.example.a1_entrega_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import java.util.*
import kotlin.system.exitProcess

class SubmitTask : AppCompatActivity() {

    private var radioValue : Int = 0
    private var isEditing : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_task)

        //getting task value if user wants to edit
        val task = intent.extras?.get("task") as? Task
        task ?.let{
            fillForm(task)
        }

        //setting spinner values
        val spinner: Spinner = findViewById(R.id.prioritySpinner)
        ArrayAdapter.createFromResource(this, R.array.taskPriority,
            android.R.layout.simple_spinner_item
        ).also { adapter -> adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter }

        //submit task btn
        val submitTaskBtn : Button = findViewById(R.id.submitTaskBtn)
        submitTaskBtn.setOnClickListener {
            playSubmitTaskAudio()
            submitTask()
        }

        val cancelBtn : Button = findViewById(R.id.cancelBtn)
        cancelBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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

        val task = Task(taskNotification.isChecked, taskPriority.selectedItem.toString(),
            taskName.text.toString(), radioValue, UUID.randomUUID().toString())

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("task", task)
        startActivity(intent)
    }

    private fun fillForm(task : Task) {
        isEditing = true

        var arrayAdapter = ArrayAdapter.createFromResource(this, R.array.taskPriority,
            android.R.layout.simple_spinner_item
        )
        val taskName = findViewById<EditText>(R.id.editTextTaskName)
        val taskPriority = findViewById<Spinner>(R.id.prioritySpinner)
        val taskNotification = findViewById<ToggleButton>(R.id.toggleBtnNotification)
        val estimateRatio = findViewById<RadioButton>(task.taskEstimative)

        taskName.setText(task.taskName)
        taskPriority.setSelection(arrayAdapter.getPosition(task.taskPriority))

        estimateRatio ?.let{
            estimateRatio.setChecked(true)
        }

        if(task.taskNotification) {
            taskNotification.setChecked(true)
        } else taskNotification.setChecked(false)
    }
}