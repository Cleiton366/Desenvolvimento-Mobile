
package com.example.a1_entrega_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var taskList : MutableList<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting arrays of tasks
        val taskObj = intent.extras?.get("taskObj") as? TaskObj
        val newTaskList = intent.extras?.get("taskList") as? MutableList<Task>

        newTaskList ?.let {
            taskList = newTaskList
        }
        taskObj ?.let{
            taskList = taskObj.taskList
        }

        allTasksListView()

        val listView : ListView = findViewById(R.id.allTasksListView)
        listView.setOnItemClickListener { parent, _, position, _ ->
            val task = taskList[position]

            val taskObj = TaskObj(taskList, task, position)

            val intent = Intent(this, SubmitTask::class.java)
            intent.putExtra("taskObj", taskObj)
            startActivity(intent)
        }

    }

    private fun allTasksListView () {
        val listView : ListView = findViewById(R.id.allTasksListView)
        val tasksArr = taskList.map { it.name }
        val arrayAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasksArr)
        listView.adapter = arrayAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                Toast.makeText(this, "Primeira entrega de Desenvolvimento de Dispositivos Moveis", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.create_new_task -> {
                val intent = Intent(this, SubmitTask::class.java)
                intent.putExtra("taskList", ArrayList(taskList))
                startActivity(intent)
                true
            }
            R.id.view_tasks -> {
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
}