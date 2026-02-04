package com.nvda.screenreader.braille

import android.content.Context
import com.nvda.screenreader.settings.NVDASettings

/**
 * خدمة برايل للتحويل من نصوص إلى برايل
 * تدعم عدة لغات وجداول برايل
 */
class BrailleService(
    private val context: Context,
    private val settings: NVDASettings
) {

    private val brailleTable = BrailleTable()

    /**
     * تحويل نص إلى برايل
     */
    fun textToBraille(text: String): String {
        return when (settings.brailleTable) {
            "ar" -> convertArabicToBraille(text)
            "en" -> convertEnglishToBraille(text)
            "fr" -> convertFrenchToBraille(text)
            "de" -> convertGermanToBraille(text)
            "es" -> convertSpanishToBraille(text)
            else -> convertArabicToBraille(text)
        }
    }

    /**
     * تحويل نص عربي إلى برايل
     */
    private fun convertArabicToBraille(text: String): String {
        val brailleText = StringBuilder()
        
        for (char in text) {
            val brailleChar = brailleTable.getArabicBraille(char)
            brailleText.append(brailleChar)
        }
        
        return brailleText.toString()
    }

    /**
     * تحويل نص إنجليزي إلى برايل
     */
    private fun convertEnglishToBraille(text: String): String {
        val brailleText = StringBuilder()
        
        for (char in text) {
            val brailleChar = brailleTable.getEnglishBraille(char)
            brailleText.append(brailleChar)
        }
        
        return brailleText.toString()
    }

    /**
     * تحويل نص فرنسي إلى برايل
     */
    private fun convertFrenchToBraille(text: String): String {
        val brailleText = StringBuilder()
        
        for (char in text) {
            val brailleChar = brailleTable.getFrenchBraille(char)
            brailleText.append(brailleChar)
        }
        
        return brailleText.toString()
    }

    /**
     * تحويل نص ألماني إلى برايل
     */
    private fun convertGermanToBraille(text: String): String {
        val brailleText = StringBuilder()
        
        for (char in text) {
            val brailleChar = brailleTable.getGermanBraille(char)
            brailleText.append(brailleChar)
        }
        
        return brailleText.toString()
    }

    /**
     * تحويل نص إسباني إلى برايل
     */
    private fun convertSpanishToBraille(text: String): String {
        val brailleText = StringBuilder()
        
        for (char in text) {
            val brailleChar = brailleTable.getSpanishBraille(char)
            brailleText.append(brailleChar)
        }
        
        return brailleText.toString()
    }

    /**
     * تحويل برايل إلى نص
     */
    fun brailleToText(braille: String): String {
        return when (settings.brailleTable) {
            "ar" -> convertBrailleToArabic(braille)
            "en" -> convertBrailleToEnglish(braille)
            "fr" -> convertBrailleToFrench(braille)
            "de" -> convertBrailleToGerman(braille)
            "es" -> convertBrailleToSpanish(braille)
            else -> convertBrailleToArabic(braille)
        }
    }

    /**
     * تحويل برايل إلى نص عربي
     */
    private fun convertBrailleToArabic(braille: String): String {
        val text = StringBuilder()
        
        for (char in braille) {
            val textChar = brailleTable.getArabicFromBraille(char)
            text.append(textChar)
        }
        
        return text.toString()
    }

    /**
     * تحويل برايل إلى نص إنجليزي
     */
    private fun convertBrailleToEnglish(braille: String): String {
        val text = StringBuilder()
        
        for (char in braille) {
            val textChar = brailleTable.getEnglishFromBraille(char)
            text.append(textChar)
        }
        
        return text.toString()
    }

    /**
     * تحويل برايل إلى نص فرنسي
     */
    private fun convertBrailleToFrench(braille: String): String {
        val text = StringBuilder()
        
        for (char in braille) {
            val textChar = brailleTable.getFrenchFromBraille(char)
            text.append(textChar)
        }
        
        return text.toString()
    }

    /**
     * تحويل برايل إلى نص ألماني
     */
    private fun convertBrailleToGerman(braille: String): String {
        val text = StringBuilder()
        
        for (char in braille) {
            val textChar = brailleTable.getGermanFromBraille(char)
            text.append(textChar)
        }
        
        return text.toString()
    }

    /**
     * تحويل برايل إلى نص إسباني
     */
    private fun convertBrailleToSpanish(braille: String): String {
        val text = StringBuilder()
        
        for (char in braille) {
            val textChar = brailleTable.getSpanishFromBraille(char)
            text.append(textChar)
        }
        
        return text.toString()
    }
}

/**
 * جداول تحويل برايل
 */
class BrailleTable {

    // جداول برايل العربية
    private val arabicBrailleMap = mapOf(
        'ا' to "⠁", 'ب' to "⠃", 'ت' to "⠞", 'ث' to "⠹",
        'ج' to "⠚", 'ح' to "⠓", 'خ' to "⠭", 'د' to "⠙",
        'ذ' to "⠮", 'ر' to "⠗", 'ز' to "⠵", 'س' to "⠎",
        'ش' to "⠱", 'ص' to "⠯", 'ض' to "⠣", 'ط' to "⠪",
        'ظ' to "⠲", 'ع' to "⠢", 'غ' to "⠬", 'ف' to "⠋",
        'ق' to "⠟", 'ك' to "⠅", 'ل' to "⠇", 'م' to "⠍",
        'ن' to "⠝", 'ه' to "⠓", 'و' to "⠺", 'ي' to "⠊",
        ' ' to "⠀", '0' to "⠴", '1' to "⠂", '2' to "⠆",
        '3' to "⠶", '4' to "⠦", '5' to "⠖", '6' to "⠬",
        '7' to "⠴", '8' to "⠪", '9' to "⠒", '.' to "⠨",
        ',' to "⠂", '!' to "⠮", '?' to "⠿"
    )

    // جداول برايل الإنجليزية
    private val englishBrailleMap = mapOf(
        'a' to "⠁", 'b' to "⠃", 'c' to "⠉", 'd' to "⠙",
        'e' to "⠑", 'f' to "⠋", 'g' to "⠛", 'h' to "⠓",
        'i' to "⠊", 'j' to "⠚", 'k' to "⠅", 'l' to "⠇",
        'm' to "⠍", 'n' to "⠝", 'o' to "⠕", 'p' to "⠏",
        'q' to "⠟", 'r' to "⠗", 's' to "⠎", 't' to "⠞",
        'u' to "⠥", 'v' to "⠧", 'w' to "⠺", 'x' to "⠭",
        'y' to "⠽", 'z' to "⠵", ' ' to "⠀", '0' to "⠴",
        '1' to "⠂", '2' to "⠆", '3' to "⠶", '4' to "⠦",
        '5' to "⠖", '6' to "⠬", '7' to "⠴", '8' to "⠪",
        '9' to "⠒", '.' to "⠨", ',' to "⠂", '!' to "⠮",
        '?' to "⠿"
    )

    // جداول برايل الفرنسية
    private val frenchBrailleMap = englishBrailleMap.toMutableMap().apply {
        put('é' to "⠫")
        put('è' to "⠪")
        put('ê' to "⠮")
        put('ë' to "⠯")
        put('à' to "⠪")
        put('â' to "⠘")
        put('ä' to "⠡")
        put('ù' to "⠾")
        put('û' to "⠳")
        put('ü' to "⠱")
        put('ô' to "⠬")
        put('ö' to "⠱")
        put('ç' to "⠡")
    }

    // جداول برايل الألمانية
    private val germanBrailleMap = englishBrailleMap.toMutableMap().apply {
        put('ä' to "⠡")
        put('ö' to "⠱")
        put('ü' to "⠱")
        put('ß' to "⠮")
    }

    // جداول برايل الإسبانية
    private val spanishBrailleMap = englishBrailleMap.toMutableMap().apply {
        put('á' to "⠁")
        put('é' to "⠑")
        put('í' to "⠊")
        put('ó' to "⠕")
        put('ú' to "⠥")
        put('ñ' to "⠝")
        put('ü' to "⠱")
    }

    fun getArabicBraille(char: Char): String = arabicBrailleMap[char] ?: "⠀"
    fun getEnglishBraille(char: Char): String = englishBrailleMap[char.lowercaseChar()] ?: "⠀"
    fun getFrenchBraille(char: Char): String = frenchBrailleMap[char.lowercaseChar()] ?: "⠀"
    fun getGermanBraille(char: Char): String = germanBrailleMap[char.lowercaseChar()] ?: "⠀"
    fun getSpanishBraille(char: Char): String = spanishBrailleMap[char.lowercaseChar()] ?: "⠀"

    fun getArabicFromBraille(char: Char): String = arabicBrailleMap.entries.find { it.value == char.toString() }?.key?.toString() ?: ""
    fun getEnglishFromBraille(char: Char): String = englishBrailleMap.entries.find { it.value == char.toString() }?.key?.toString() ?: ""
    fun getFrenchFromBraille(char: Char): String = frenchBrailleMap.entries.find { it.value == char.toString() }?.key?.toString() ?: ""
    fun getGermanFromBraille(char: Char): String = germanBrailleMap.entries.find { it.value == char.toString() }?.key?.toString() ?: ""
    fun getSpanishFromBraille(char: Char): String = spanishBrailleMap.entries.find { it.value == char.toString() }?.key?.toString() ?: ""
}
