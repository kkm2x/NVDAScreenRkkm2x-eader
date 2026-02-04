package com.nvda.screenreader.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.nvda.screenreader.R
import com.nvda.screenreader.braille.BrailleService
import com.nvda.screenreader.settings.NVDASettings

/**
 * نشاط برايل
 * يقوم بتحويل النصوص إلى برايل
 */
class BrailleActivity : AppCompatActivity() {

    private lateinit var inputText: EditText
    private lateinit var outputText: TextView
    private lateinit var brailleTableSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var reverseButton: Button
    
    private lateinit var brailleService: BrailleService
    private lateinit var settings: NVDASettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_braille)
        
        // تهيئة الإعدادات
        settings = NVDASettings(this)
        
        // تهيئة خدمة برايل
        brailleService = BrailleService(this, settings)
        
        // تهيئة العناصر
        inputText = findViewById(R.id.input_text)
        outputText = findViewById(R.id.output_text)
        brailleTableSpinner = findViewById(R.id.braille_table_spinner)
        convertButton = findViewById(R.id.convert_button)
        reverseButton = findViewById(R.id.reverse_button)
        
        // إعداد Spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.braille_table_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        brailleTableSpinner.adapter = adapter
        
        // تعيين المستمعين
        convertButton.setOnClickListener { convertToBraille() }
        reverseButton.setOnClickListener { convertFromBraille() }
    }

    private fun convertToBraille() {
        val text = inputText.text.toString()
        if (text.isEmpty()) {
            outputText.text = "يرجى إدخال نص"
            return
        }
        
        val brailleText = brailleService.textToBraille(text)
        outputText.text = brailleText
    }

    private fun convertFromBraille() {
        val text = inputText.text.toString()
        if (text.isEmpty()) {
            outputText.text = "يرجى إدخال برايل"
            return
        }
        
        val plainText = brailleService.brailleToText(text)
        outputText.text = plainText
    }
}
