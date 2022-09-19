package com.example.a1_entrega_mobile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class GridView : AppCompatActivity() {
    // on below line we are creating
    // variables for grid view and course list
    lateinit var courseGRV: GridView
    lateinit var courseList: List<GridViewModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view)

        // initializing variables of grid view with their ids.
        courseGRV = findViewById(R.id.grid_view)
        courseList = ArrayList<GridViewModal>()

        // on below line we are adding data to
        // our course list with image and course name.
        courseList = courseList + GridViewModal("C++", R.drawable.c)
        courseList = courseList + GridViewModal("Java", R.drawable.java)
        courseList = courseList + GridViewModal("Android", R.drawable.android)
        courseList = courseList + GridViewModal("Python", R.drawable.python)
        courseList = courseList + GridViewModal("Javascript", R.drawable.js)

        // on below line we are initializing our course adapter
        // and passing course list and context.
        val courseAdapter = GridRVAdapter(courseList = courseList, this@GridView)

        // on below line we are setting adapter to our grid view.
        courseGRV.adapter = courseAdapter

        // on below line we are adding on item
        // click listener for our grid view.
        courseGRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // inside on click method we are simply displaying
            // a toast message with course name.
            Toast.makeText(
                applicationContext, courseList[position].courseName + " selected",
                Toast.LENGTH_SHORT
            ).show()
        }

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
                val intent = Intent(this,  MainActivity::class.java)
                startActivity(intent)
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
                true
            }
            else -> exitProcess(-1)
        }
    }
}