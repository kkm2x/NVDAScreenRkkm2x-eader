package com.nvda.screenreader.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.nvda.screenreader.R

/**
 * قائمة NVDA الرئيسية
 * تحتوي على جميع خيارات NVDA
 */
class NVDAMenuActivity : AppCompatActivity() {

    private lateinit var menuListView: ListView
    
    private val menuItems = listOf(
        "التفضيلات",
        "الأدوات",
        "المساعدة",
        "حول NVDA",
        "الخروج"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nvda_menu)
        
        menuListView = findViewById(R.id.menu_list_view)
        
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            menuItems
        )
        
        menuListView.adapter = adapter
        menuListView.setOnItemClickListener { _, _, position, _ ->
            handleMenuSelection(position)
        }
    }

    private fun handleMenuSelection(position: Int) {
        when (position) {
            0 -> openPreferences()
            1 -> openTools()
            2 -> openHelp()
            3 -> showAbout()
            4 -> exitApp()
        }
    }

    private fun openPreferences() {
        val intent = Intent(this, PreferencesActivity::class.java)
        startActivity(intent)
    }

    private fun openTools() {
        val intent = Intent(this, ToolsActivity::class.java)
        startActivity(intent)
    }

    private fun openHelp() {
        val intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }

    private fun showAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun exitApp() {
        finish()
    }
}
