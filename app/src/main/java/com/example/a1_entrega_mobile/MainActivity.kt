
package com.example.a1_entrega_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        allTasksListView()
    }

    private fun allTasksListView () {
        val listView : ListView = findViewById(R.id.allTasksListView)
        val tasksArr = arrayListOf("Learn Kotlin","Do the laundry","Study for the test on friday",
            "Do the grocery","Walk with the dog","Do the dishes")
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
                val intent = Intent(this,  SubmitTask::class.java)
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
            R.id.tabs -> {
                val intent = Intent(this,  SwipeViewActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.gridView -> {
                val intent = Intent(this,  GridView::class.java)
                startActivity(intent)
                true
            }
            else -> exitProcess(-1)
        }
    }
}