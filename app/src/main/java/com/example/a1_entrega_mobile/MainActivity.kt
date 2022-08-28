package com.example.a1_entrega_mobile

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import java.io.IOException
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting spinner values
        val spinner: Spinner = findViewById(R.id.prioritySpinner)
        ArrayAdapter.createFromResource(this, R.array.taskPriority,
            android.R.layout.simple_spinner_item
        ).also { adapter -> adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter }

        //submit task btn
        val submitTaskBtn : Button = findViewById(R.id.submitTaskBtn)
        submitTaskBtn.setOnClickListener() {
            playSubmitTaskAudio()
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
        }

        }

    // render menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true;
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
                var intent = Intent(this,  AllTasksActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.user_profile -> {
                var intent = Intent(this,  UserProfile::class.java)
                startActivity(intent)
                true
            }
            else -> exitProcess(-1)
        }
    }

    //play audios given an url
    fun playSubmitTaskAudio() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.submittasksound)
        mediaPlayer.start()
    }
}