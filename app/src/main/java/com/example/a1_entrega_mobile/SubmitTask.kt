package com.example.a1_entrega_mobile

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.system.exitProcess

class SubmitTask : AppCompatActivity() {

    private var radioValue : Int = 0
    private var isEditing : Boolean = false
    private var taskId : String = ""

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
        val task = intent.extras?.get("task") as? Task

        task ?.let{
            isEditing = true
            radioValue = task.estimative
            taskId = task.id
            fillForm(task)
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
            startActivity(intent)
        }

        //delete bnt
        val deleteBtn : Button = findViewById(R.id.deleteBtn)
        deleteBtn.setOnClickListener {
            val task = Task(false, 0,
                "", radioValue, taskId)
            runBlocking {
                val client = HttpClient(CIO) {
                    install(ContentNegotiation) {
                        gson()
                    }
                }
                val res : HttpResponse = client.delete("http://192.168.0.6:4000/delete-task") {
                    contentType(ContentType.Application.Json)
                    setBody(task)
                }
                if(res.status.value == 200) {
                    println("success")
                }
                client.close()
            }
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

        if(isEditing) {
            val task = Task(taskNotification.isChecked, taskPriority.selectedItemPosition,
                taskName.text.toString(), radioValue, taskId)
            runBlocking {
                val client = HttpClient(CIO) {
                    install(ContentNegotiation) {
                        gson()
                    }
                }
                val res : HttpResponse = client.put("http://192.168.0.6:4000/update-task") {
                    contentType(ContentType.Application.Json)
                    setBody(task)
                }
                if(res.status.value == 200) {
                    println("success")
                }
                client.close()
            }
        } else {
            val task = Task(taskNotification.isChecked, taskPriority.selectedItemPosition,
                taskName.text.toString(), radioValue, UUID.randomUUID().toString())
            runBlocking {
                val client = HttpClient(CIO) {
                    install(ContentNegotiation) {
                        gson()
                    }
                }
                val res : HttpResponse = client.post("http://192.168.0.6:4000/submit-task") {
                    contentType(ContentType.Application.Json)
                    setBody(task)
                }
                if(res.status.value == 201) {
                    println("success")
                }
                client.close()
            }
        }

        val intent = Intent(this, MainActivity::class.java)
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