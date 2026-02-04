package com.nvda.screenreader.settings

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * فئة إدارة إعدادات NVDA الكاملة
 * تخزن واستعادة جميع الإعدادات
 */
class NVDASettings(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "nvda_settings",
        Context.MODE_PRIVATE
    )
    private val gson = Gson()

    // ============ إعدادات الصوت ============
    
    /**
     * محرك التحويل النصي (Google, eSpeak, OneCore)
     */
    var synthesizer: String
        get() = prefs.getString("synthesizer", "Google") ?: "Google"
        set(value) = prefs.edit().putString("synthesizer", value).apply()

    /**
     * الصوت المستخدم
     */
    var voice: String
        get() = prefs.getString("voice", "ar-SA") ?: "ar-SA"
        set(value) = prefs.edit().putString("voice", value).apply()

    /**
     * سرعة القراءة (0.5 - 2.0)
     */
    var speechRate: Float
        get() = prefs.getFloat("speech_rate", 1.0f)
        set(value) = prefs.edit().putFloat("speech_rate", value.coerceIn(0.5f, 2.0f)).apply()

    /**
     * مستوى الصوت (0.0 - 1.0)
     */
    var volume: Float
        get() = prefs.getFloat("volume", 0.8f)
        set(value) = prefs.edit().putFloat("volume", value.coerceIn(0.0f, 1.0f)).apply()

    /**
     * درجة الصوت (0.0 - 2.0)
     */
    var pitch: Float
        get() = prefs.getFloat("pitch", 1.0f)
        set(value) = prefs.edit().putFloat("pitch", value.coerceIn(0.0f, 2.0f)).apply()

    /**
     * تنويع النبرة (0 - 100)
     */
    var inflection: Int
        get() = prefs.getInt("inflection", 50)
        set(value) = prefs.edit().putInt("inflection", value.coerceIn(0, 100)).apply()

    /**
     * مستوى نطق الرموز (0 - 3)
     * 0: لا شيء
     * 1: بعض الرموز
     * 2: معظم الرموز
     * 3: جميع الرموز
     */
    var punctuationLevel: Int
        get() = prefs.getInt("punctuation_level", 1)
        set(value) = prefs.edit().putInt("punctuation_level", value.coerceIn(0, 3)).apply()

    /**
     * قراءة الأحرف الكبيرة
     */
    var announceCapitals: Boolean
        get() = prefs.getBoolean("announce_capitals", true)
        set(value) = prefs.edit().putBoolean("announce_capitals", value).apply()

    /**
     * قراءة الأرقام برقم
     */
    var sayNumbers: Boolean
        get() = prefs.getBoolean("say_numbers", true)
        set(value) = prefs.edit().putBoolean("say_numbers", value).apply()

    // ============ أوضاع القراءة ============

    /**
     * وضع القراءة الحالي
     * talk: قراءة كاملة
     * beeps: أصوات فقط
     * off: بدون صوت
     */
    var speechMode: String
        get() = prefs.getString("speech_mode", "talk") ?: "talk"
        set(value) = prefs.edit().putString("speech_mode", value).apply()

    /**
     * وضع المراجعة الحالي
     * object: مراجعة الكائن
     * document: مراجعة المستند
     * screen: مراجعة الشاشة
     */
    var reviewMode: String
        get() = prefs.getString("review_mode", "object") ?: "object"
        set(value) = prefs.edit().putString("review_mode", value).apply()

    /**
     * وضع التنقل الحالي
     * browse: وضع التصفح
     * focus: وضع التركيز
     * object: وضع الكائنات
     */
    var navigationMode: String
        get() = prefs.getString("navigation_mode", "browse") ?: "browse"
        set(value) = prefs.edit().putString("navigation_mode", value).apply()

    // ============ إعدادات الإعلانات ============

    /**
     * قراءة تلقائية عند فتح شاشة جديدة
     */
    var autoRead: Boolean
        get() = prefs.getBoolean("auto_read", true)
        set(value) = prefs.edit().putBoolean("auto_read", value).apply()

    /**
     * الإعلان عن تغييرات الشاشة
     */
    var announceScreenChanges: Boolean
        get() = prefs.getBoolean("announce_screen_changes", true)
        set(value) = prefs.edit().putBoolean("announce_screen_changes", value).apply()

    /**
     * الإعلان عن حقول النماذج
     */
    var announceFormFields: Boolean
        get() = prefs.getBoolean("announce_form_fields", true)
        set(value) = prefs.edit().putBoolean("announce_form_fields", value).apply()

    /**
     * الإعلان عن الروابط
     */
    var announceLinks: Boolean
        get() = prefs.getBoolean("announce_links", true)
        set(value) = prefs.edit().putBoolean("announce_links", value).apply()

    /**
     * الإعلان عن الصور
     */
    var announceGraphics: Boolean
        get() = prefs.getBoolean("announce_graphics", true)
        set(value) = prefs.edit().putBoolean("announce_graphics", value).apply()

    /**
     * الإعلان عن الأزرار
     */
    var announceButtons: Boolean
        get() = prefs.getBoolean("announce_buttons", true)
        set(value) = prefs.edit().putBoolean("announce_buttons", value).apply()

    /**
     * الإعلان عن القوائم
     */
    var announceMenus: Boolean
        get() = prefs.getBoolean("announce_menus", true)
        set(value) = prefs.edit().putBoolean("announce_menus", value).apply()

    /**
     * الإعلان عن الجداول
     */
    var announceTables: Boolean
        get() = prefs.getBoolean("announce_tables", true)
        set(value) = prefs.edit().putBoolean("announce_tables", value).apply()

    /**
     * الإعلان عن الإطارات
     */
    var announceFrames: Boolean
        get() = prefs.getBoolean("announce_frames", true)
        set(value) = prefs.edit().putBoolean("announce_frames", value).apply()

    /**
     * الإعلان عن هيكل الصفحة
     */
    var announcePageStructure: Boolean
        get() = prefs.getBoolean("announce_page_structure", true)
        set(value) = prefs.edit().putBoolean("announce_page_structure", value).apply()

    /**
     * الإعلان عن التركيز
     */
    var announceEmphasis: Boolean
        get() = prefs.getBoolean("announce_emphasis", true)
        set(value) = prefs.edit().putBoolean("announce_emphasis", value).apply()

    // ============ إعدادات البصرية ============

    /**
     * تمييز العنصر الحالي بصرياً
     */
    var visualHighlight: Boolean
        get() = prefs.getBoolean("visual_highlight", true)
        set(value) = prefs.edit().putBoolean("visual_highlight", value).apply()

    /**
     * تمييز التركيز بصرياً
     */
    var focusHighlight: Boolean
        get() = prefs.getBoolean("focus_highlight", true)
        set(value) = prefs.edit().putBoolean("focus_highlight", value).apply()

    /**
     * تمييز المؤشر بصرياً
     */
    var caretHighlight: Boolean
        get() = prefs.getBoolean("caret_highlight", true)
        set(value) = prefs.edit().putBoolean("caret_highlight", value).apply()

    /**
     * لون التمييز
     */
    var highlightColor: String
        get() = prefs.getString("highlight_color", "#0a7ea4") ?: "#0a7ea4"
        set(value) = prefs.edit().putString("highlight_color", value).apply()

    /**
     * سمك التمييز
     */
    var highlightThickness: Int
        get() = prefs.getInt("highlight_thickness", 3)
        set(value) = prefs.edit().putInt("highlight_thickness", value.coerceIn(1, 10)).apply()

    // ============ إعدادات اللمس ============

    /**
     * وضع الكتابة باللمس
     */
    var touchTypingMode: Boolean
        get() = prefs.getBoolean("touch_typing_mode", false)
        set(value) = prefs.edit().putBoolean("touch_typing_mode", value).apply()

    /**
     * لوحة مفاتيح اللمس
     */
    var touchKeyboard: Boolean
        get() = prefs.getBoolean("touch_keyboard", true)
        set(value) = prefs.edit().putBoolean("touch_keyboard", value).apply()

    /**
     * النقر لتفعيل
     */
    var tapToActivate: Boolean
        get() = prefs.getBoolean("tap_to_activate", true)
        set(value) = prefs.edit().putBoolean("tap_to_activate", value).apply()

    /**
     * حساسية الحركات (0 - 100)
     */
    var flickSensitivity: Int
        get() = prefs.getInt("flick_sensitivity", 50)
        set(value) = prefs.edit().putInt("flick_sensitivity", value.coerceIn(0, 100)).apply()

    // ============ إعدادات لوحة المفاتيح ============

    /**
     * مفتاح تعديل NVDA
     * capsLock أو insert
     */
    var nVDAModifierKey: String
        get() = prefs.getString("nvda_modifier_key", "capsLock") ?: "capsLock"
        set(value) = prefs.edit().putString("nvda_modifier_key", value).apply()

    /**
     * تخطيط لوحة المفاتيح
     * desktop أو laptop
     */
    var keyboardLayout: String
        get() = prefs.getString("keyboard_layout", "desktop") ?: "desktop"
        set(value) = prefs.edit().putString("keyboard_layout", value).apply()

    /**
     * تفعيل ربط المفاتيح
     */
    var enableKeyBindings: Boolean
        get() = prefs.getBoolean("enable_key_bindings", true)
        set(value) = prefs.edit().putBoolean("enable_key_bindings", value).apply()

    // ============ إعدادات برايل ============

    /**
     * تفعيل دعم برايل
     */
    var brailleEnabled: Boolean
        get() = prefs.getBoolean("braille_enabled", false)
        set(value) = prefs.edit().putBoolean("braille_enabled", value).apply()

    /**
     * جدول برايل
     */
    var brailleTable: String
        get() = prefs.getString("braille_table", "ar") ?: "ar"
        set(value) = prefs.edit().putString("braille_table", value).apply()

    // ============ إعدادات متقدمة ============

    /**
     * تفعيل OCR
     */
    var ocrEnabled: Boolean
        get() = prefs.getBoolean("ocr_enabled", false)
        set(value) = prefs.edit().putBoolean("ocr_enabled", value).apply()

    /**
     * لغة OCR
     */
    var ocrLanguage: String
        get() = prefs.getString("ocr_language", "ar") ?: "ar"
        set(value) = prefs.edit().putString("ocr_language", value).apply()

    /**
     * حفظ السجل
     */
    var enableLogging: Boolean
        get() = prefs.getBoolean("enable_logging", false)
        set(value) = prefs.edit().putBoolean("enable_logging", value).apply()

    /**
     * تحسين الأداء
     */
    var performanceMode: Boolean
        get() = prefs.getBoolean("performance_mode", false)
        set(value) = prefs.edit().putBoolean("performance_mode", value).apply()

    /**
     * إعادة تعيين جميع الإعدادات إلى الافتراضية
     */
    fun resetToDefaults() {
        prefs.edit().clear().apply()
    }

    /**
     * حفظ جميع الإعدادات إلى ملف JSON
     */
    fun exportSettings(): String {
        val settings = mapOf(
            "synthesizer" to synthesizer,
            "voice" to voice,
            "speechRate" to speechRate,
            "volume" to volume,
            "pitch" to pitch,
            "inflection" to inflection,
            "punctuationLevel" to punctuationLevel,
            "announceCapitals" to announceCapitals,
            "sayNumbers" to sayNumbers,
            "speechMode" to speechMode,
            "reviewMode" to reviewMode,
            "navigationMode" to navigationMode,
            "autoRead" to autoRead,
            "announceScreenChanges" to announceScreenChanges,
            "announceFormFields" to announceFormFields,
            "announceLinks" to announceLinks,
            "announceGraphics" to announceGraphics,
            "announceButtons" to announceButtons,
            "announceMenus" to announceMenus,
            "announceTables" to announceTables,
            "announceFrames" to announceFrames,
            "announcePageStructure" to announcePageStructure,
            "announceEmphasis" to announceEmphasis,
            "visualHighlight" to visualHighlight,
            "focusHighlight" to focusHighlight,
            "caretHighlight" to caretHighlight,
            "highlightColor" to highlightColor,
            "highlightThickness" to highlightThickness,
            "touchTypingMode" to touchTypingMode,
            "touchKeyboard" to touchKeyboard,
            "tapToActivate" to tapToActivate,
            "flickSensitivity" to flickSensitivity,
            "nVDAModifierKey" to nVDAModifierKey,
            "keyboardLayout" to keyboardLayout,
            "enableKeyBindings" to enableKeyBindings,
            "brailleEnabled" to brailleEnabled,
            "brailleTable" to brailleTable,
            "ocrEnabled" to ocrEnabled,
            "ocrLanguage" to ocrLanguage,
            "enableLogging" to enableLogging,
            "performanceMode" to performanceMode
        )
        return gson.toJson(settings)
    }

    /**
     * استيراد الإعدادات من ملف JSON
     */
    fun importSettings(json: String) {
        try {
            val settings = gson.fromJson(json, Map::class.java)
            settings.forEach { (key, value) ->
                when (key) {
                    "synthesizer" -> synthesizer = value.toString()
                    "voice" -> voice = value.toString()
                    "speechRate" -> speechRate = (value as Number).toFloat()
                    "volume" -> volume = (value as Number).toFloat()
                    "pitch" -> pitch = (value as Number).toFloat()
                    "inflection" -> inflection = (value as Number).toInt()
                    "punctuationLevel" -> punctuationLevel = (value as Number).toInt()
                    "announceCapitals" -> announceCapitals = value as Boolean
                    "sayNumbers" -> sayNumbers = value as Boolean
                    "speechMode" -> speechMode = value.toString()
                    "reviewMode" -> reviewMode = value.toString()
                    "navigationMode" -> navigationMode = value.toString()
                    "autoRead" -> autoRead = value as Boolean
                    "announceScreenChanges" -> announceScreenChanges = value as Boolean
                    "announceFormFields" -> announceFormFields = value as Boolean
                    "announceLinks" -> announceLinks = value as Boolean
                    "announceGraphics" -> announceGraphics = value as Boolean
                    "announceButtons" -> announceButtons = value as Boolean
                    "announceMenus" -> announceMenus = value as Boolean
                    "announceTables" -> announceTables = value as Boolean
                    "announceFrames" -> announceFrames = value as Boolean
                    "announcePageStructure" -> announcePageStructure = value as Boolean
                    "announceEmphasis" -> announceEmphasis = value as Boolean
                    "visualHighlight" -> visualHighlight = value as Boolean
                    "focusHighlight" -> focusHighlight = value as Boolean
                    "caretHighlight" -> caretHighlight = value as Boolean
                    "highlightColor" -> highlightColor = value.toString()
                    "highlightThickness" -> highlightThickness = (value as Number).toInt()
                    "touchTypingMode" -> touchTypingMode = value as Boolean
                    "touchKeyboard" -> touchKeyboard = value as Boolean
                    "tapToActivate" -> tapToActivate = value as Boolean
                    "flickSensitivity" -> flickSensitivity = (value as Number).toInt()
                    "nVDAModifierKey" -> nVDAModifierKey = value.toString()
                    "keyboardLayout" -> keyboardLayout = value.toString()
                    "enableKeyBindings" -> enableKeyBindings = value as Boolean
                    "brailleEnabled" -> brailleEnabled = value as Boolean
                    "brailleTable" -> brailleTable = value.toString()
                    "ocrEnabled" -> ocrEnabled = value as Boolean
                    "ocrLanguage" -> ocrLanguage = value.toString()
                    "enableLogging" -> enableLogging = value as Boolean
                    "performanceMode" -> performanceMode = value as Boolean
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
