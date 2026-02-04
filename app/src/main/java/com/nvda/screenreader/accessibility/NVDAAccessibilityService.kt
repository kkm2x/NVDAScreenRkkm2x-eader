package com.nvda.screenreader.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.speech.tts.TextToSpeech
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.nvda.screenreader.settings.NVDASettings
import java.util.Locale

/**
 * خدمة الوصولية NVDA الرئيسية
 * تقرأ جميع العناصر على الشاشة من أي تطبيق
 * مع دعم كامل لجميع أوضاع NVDA والإعدادات
 */
class NVDAAccessibilityService : AccessibilityService(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var settings: NVDASettings
    private var isSpeaking = false
    private var isServiceEnabled = true
    private var currentElement: AccessibilityNodeInfo? = null
    private var elementStack: MutableList<AccessibilityNodeInfo> = mutableListOf()

    override fun onServiceConnected() {
        super.onServiceConnected()
        
        // تهيئة الإعدادات
        settings = NVDASettings(this)
        
        // تهيئة Text-to-Speech
        textToSpeech = TextToSpeech(this, this)
        
        // إعدادات الخدمة
        val info = AccessibilityServiceInfo()
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or
                AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE or
                AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS or
                AccessibilityServiceInfo.FLAG_REPORT_ACCESSIBILITY_CONTEXT
        info.notificationTimeout = 100
        
        serviceInfo = info
        
        // إخبار المستخدم بتفعيل الخدمة
        if (settings.autoRead) {
            speak("قارئ الشاشة NVDA مفعل الآن")
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (!isServiceEnabled || event == null) return

        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                if (settings.announceScreenChanges) {
                    val packageName = event.packageName?.toString() ?: "تطبيق غير معروف"
                    speak("تم فتح $packageName")
                    
                    if (settings.autoRead) {
                        readScreenContent()
                    }
                }
            }
            
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                if (settings.announceFormFields || settings.announceButtons) {
                    val text = event.text.joinToString(" ")
                    val contentDescription = event.contentDescription?.toString() ?: ""
                    
                    if (text.isNotEmpty() || contentDescription.isNotEmpty()) {
                        val fullText = if (text.isNotEmpty()) text else contentDescription
                        speak(fullText)
                    }
                }
            }
            
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                speak("تم تفعيل العنصر")
            }
            
            AccessibilityEvent.TYPE_ANNOUNCEMENT -> {
                val text = event.text.joinToString(" ")
                if (text.isNotEmpty()) {
                    speak(text)
                }
            }
            
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val text = event.text.joinToString(" ")
                if (text.isNotEmpty()) {
                    speak(text)
                }
            }
        }
    }

    override fun onInterrupt() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("ar", "SA"))
            
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech.setLanguage(Locale.getDefault())
            }
            
            updateTTSSettings()
        }
    }

    /**
     * تحديث إعدادات Text-to-Speech من الإعدادات المحفوظة
     */
    private fun updateTTSSettings() {
        textToSpeech.setSpeechRate(settings.speechRate)
        textToSpeech.setPitch(settings.pitch)
    }

    /**
     * قراءة محتوى الشاشة بالكامل (Screen Review Mode)
     */
    private fun readScreenContent() {
        val rootNode = rootInActiveWindow ?: return
        val screenContent = StringBuilder()
        
        traverseNodeTree(rootNode, screenContent)
        
        if (screenContent.isNotEmpty()) {
            speak(screenContent.toString())
        }
    }

    /**
     * المرور عبر شجرة العناصر وجمع النصوص
     */
    private fun traverseNodeTree(node: AccessibilityNodeInfo, content: StringBuilder) {
        val text = node.text?.toString() ?: ""
        val contentDescription = node.contentDescription?.toString() ?: ""
        val className = node.className?.toString() ?: ""
        
        // إضافة وصف العنصر
        if (text.isNotEmpty()) {
            content.append("$text. ")
        }
        if (contentDescription.isNotEmpty() && contentDescription != text) {
            content.append("$contentDescription. ")
        }
        
        // إضافة معلومات الدور
        val role = getRoleDescription(className)
        if (role.isNotEmpty()) {
            content.append("$role. ")
        }
        
        // المرور عبر العناصر الفرعية
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child != null) {
                traverseNodeTree(child, content)
                child.recycle()
            }
        }
    }

    /**
     * الحصول على وصف دور العنصر
     */
    private fun getRoleDescription(className: String): String {
        return when {
            className.contains("Button") -> "زر"
            className.contains("EditText") -> "حقل إدخال"
            className.contains("CheckBox") -> "صندوق اختيار"
            className.contains("RadioButton") -> "زر اختيار"
            className.contains("Switch") -> "مفتاح"
            className.contains("TextView") -> "نص"
            className.contains("ImageView") -> "صورة"
            className.contains("ListView") -> "قائمة"
            className.contains("GridView") -> "شبكة"
            className.contains("WebView") -> "صفحة ويب"
            else -> ""
        }
    }

    /**
     * قراءة نص باستخدام Text-to-Speech
     */
    private fun speak(text: String) {
        if (text.isEmpty() || !isServiceEnabled) return
        
        if (settings.speechMode == "off") return
        
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        
        isSpeaking = true
        
        val params = android.os.Bundle().apply {
            putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, settings.volume)
        }
        
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params)
    }

    /**
     * التنقل للعنصر التالي (Object Navigation)
     */
    fun navigateNext() {
        val rootNode = rootInActiveWindow ?: return
        val nextNode = findNextElement(rootNode)
        if (nextNode != null) {
            currentElement = nextNode
            announceElement(nextNode)
        }
    }

    /**
     * التنقل للعنصر السابق
     */
    fun navigatePrevious() {
        val rootNode = rootInActiveWindow ?: return
        val prevNode = findPreviousElement(rootNode)
        if (prevNode != null) {
            currentElement = prevNode
            announceElement(prevNode)
        }
    }

    /**
     * البحث عن العنصر التالي
     */
    private fun findNextElement(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (child != null) {
                if (isInteractiveElement(child)) {
                    return child
                }
                val result = findNextElement(child)
                if (result != null) return result
            }
        }
        return null
    }

    /**
     * البحث عن العنصر السابق
     */
    private fun findPreviousElement(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        for (i in node.childCount - 1 downTo 0) {
            val child = node.getChild(i)
            if (child != null) {
                if (isInteractiveElement(child)) {
                    return child
                }
                val result = findPreviousElement(child)
                if (result != null) return result
            }
        }
        return null
    }

    /**
     * التحقق من أن العنصر تفاعلي
     */
    private fun isInteractiveElement(node: AccessibilityNodeInfo): Boolean {
        return node.isClickable || node.isLongClickable || node.isCheckable || node.isEditable
    }

    /**
     * الإعلان عن العنصر
     */
    private fun announceElement(node: AccessibilityNodeInfo) {
        val text = node.text?.toString() ?: ""
        val contentDescription = node.contentDescription?.toString() ?: ""
        val announcement = if (text.isNotEmpty()) text else contentDescription
        
        if (announcement.isNotEmpty()) {
            speak(announcement)
        }
    }

    /**
     * تعيين سرعة القراءة
     */
    fun setSpeechRate(rate: Float) {
        settings.speechRate = rate
        textToSpeech.setSpeechRate(rate)
    }

    /**
     * تعيين مستوى الصوت
     */
    fun setVolume(volume: Float) {
        settings.volume = volume
    }

    /**
     * تعيين درجة الصوت
     */
    fun setPitch(pitch: Float) {
        settings.pitch = pitch
        textToSpeech.setPitch(pitch)
    }

    /**
     * تعيين وضع القراءة
     */
    fun setSpeechMode(mode: String) {
        settings.speechMode = mode
    }

    /**
     * تعيين وضع المراجعة
     */
    fun setReviewMode(mode: String) {
        settings.reviewMode = mode
    }

    /**
     * تعيين وضع التنقل
     */
    fun setNavigationMode(mode: String) {
        settings.navigationMode = mode
    }

    /**
     * تفعيل/تعطيل الخدمة
     */
    fun setServiceEnabled(enabled: Boolean) {
        isServiceEnabled = enabled
        if (!enabled && textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
    }
}
