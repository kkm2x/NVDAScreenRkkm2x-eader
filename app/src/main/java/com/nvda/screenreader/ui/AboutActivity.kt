package com.nvda.screenreader.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nvda.screenreader.R

/**
 * نشاط حول التطبيق
 */
class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        
        val aboutText = findViewById<TextView>(R.id.about_text)
        aboutText.text = """
            قارئ الشاشة NVDA للأندرويد
            الإصدار: 1.0.0
            
            قارئ شاشة متقدم يقرأ جميع محتويات الشاشة من أي تطبيق.
            
            المميزات:
            - قراءة شاملة للشاشة
            - دعم كامل للغة العربية
            - إعدادات متقدمة
            - دعم برايل
            - OCR
            
            الترخيص: GPL v3
            
            شكر خاص لـ NVDA وفريق التطوير
        """.trimIndent()
    }
}
