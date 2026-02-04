package com.nvda.screenreader

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity
import com.nvda.screenreader.accessibility.NVDAAccessibilityService
import com.nvda.screenreader.settings.NVDASettings
import com.nvda.screenreader.ui.NVDAMenuActivity
import java.util.Locale

/**
 * الشاشة الرئيسية للتطبيق
 */
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var statusText: TextView
    private lateinit var toggleButton: Button
    private lateinit var volumeSeekBar: SeekBar
    private lateinit var speedSeekBar: SeekBar
    private lateinit var settingsButton: Button
    private lateinit var menuButton: Button
    
    private lateinit var settings: NVDASettings
    private lateinit var textToSpeech: TextToSpeech
    private var isServiceEnabled = false
    private lateinit var accessibilityManager: AccessibilityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // تهيئة الإعدادات
        settings = NVDASettings(this)
        
        // تهيئة Text-to-Speech
        textToSpeech = TextToSpeech(this, this)
        
        // تهيئة Accessibility Manager
        accessibilityManager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        
        // تهيئة العناصر
        initializeViews()
        
        // تحديث حالة الخدمة
        updateServiceStatus()
        
        // إذا كانت الخدمة معطلة، اطلب من المستخدم تفعيلها
        if (!isServiceEnabled) {
            showAccessibilitySettings()
        }
    }

    private fun initializeViews() {
        statusText = findViewById(R.id.status_text)
        toggleButton = findViewById(R.id.toggle_button)
        volumeSeekBar = findViewById(R.id.volume_seekbar)
        speedSeekBar = findViewById(R.id.speed_seekbar)
        settingsButton = findViewById(R.id.settings_button)
        menuButton = findViewById(R.id.menu_button)
        
        // تعيين المستمعين
        toggleButton.setOnClickListener { toggleService() }
        settingsButton.setOnClickListener { openAccessibilitySettings() }
        menuButton.setOnClickListener { openNVDAMenu() }
        
        // تعيين منزلقات التحكم
        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    settings.volume = progress / 100.0f
                    speak("مستوى الصوت: $progress بالمئة")
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        speedSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val rate = 0.5f + (progress / 100.0f * 1.5f)
                    settings.speechRate = rate
                    textToSpeech.setSpeechRate(rate)
                    speak("سرعة القراءة: ${String.format("%.1f", rate)}")
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // تحميل الإعدادات المحفوظة
        volumeSeekBar.progress = (settings.volume * 100).toInt()
        speedSeekBar.progress = ((settings.speechRate - 0.5f) / 1.5f * 100).toInt()
    }

    private fun updateServiceStatus() {
        isServiceEnabled = isAccessibilityServiceEnabled()
        
        if (isServiceEnabled) {
            statusText.text = "قارئ الشاشة: مفعل ✓"
            toggleButton.text = "إيقاف القارئ"
            statusText.contentDescription = "قارئ الشاشة مفعل"
        } else {
            statusText.text = "قارئ الشاشة: معطل"
            toggleButton.text = "تشغيل القارئ"
            statusText.contentDescription = "قارئ الشاشة معطل"
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val enabledServices = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false
        
        val componentName = "${packageName}/${NVDAAccessibilityService::class.java.name}"
        return enabledServices.contains(componentName)
    }

    private fun toggleService() {
        if (isServiceEnabled) {
            disableService()
        } else {
            enableService()
        }
    }

    private fun enableService() {
        speak("جاري تفعيل قارئ الشاشة")
        showAccessibilitySettings()
    }

    private fun disableService() {
        speak("جاري تعطيل قارئ الشاشة")
        isServiceEnabled = false
        updateServiceStatus()
    }

    private fun showAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }

    private fun openAccessibilitySettings() {
        showAccessibilitySettings()
    }

    private fun openNVDAMenu() {
        val intent = Intent(this, NVDAMenuActivity::class.java)
        startActivity(intent)
    }

    private fun speak(text: String) {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        
        val params = Bundle().apply {
            putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, settings.volume)
        }
        
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("ar", "SA"))
            
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech.setLanguage(Locale.getDefault())
            }
            
            textToSpeech.setSpeechRate(settings.speechRate)
            textToSpeech.setPitch(settings.pitch)
        }
    }

    override fun onResume() {
        super.onResume()
        updateServiceStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
    }
}
