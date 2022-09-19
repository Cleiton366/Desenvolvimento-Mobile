package com.example.a1_entrega_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import kotlin.system.exitProcess

class SwipeViewActivity : AppCompatActivity() {

    private var tittlesList = mutableListOf<String>()
    private var aboutsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_view)

        postToList()
        val viewPager2 : ViewPager2 = findViewById(R.id.view_pager2)
        viewPager2.adapter = SwipeViewPageAdapter(tittlesList, aboutsList)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator : CircleIndicator3 = findViewById(R.id.swipe_view_indicator)
        indicator.setViewPager(viewPager2)
    }

    private fun addToList(tittle : String, about: String) {
        tittlesList.add(tittle)
        aboutsList.add(about)
    }

    private fun postToList() {
        for(i in 1..5) {
            addToList("Tittle $i", "About $i")
        }
    }

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
                val intent = Intent(this,  GridView::class.java)
                startActivity(intent)
                true
            }
            else -> exitProcess(-1)
        }
    }
}