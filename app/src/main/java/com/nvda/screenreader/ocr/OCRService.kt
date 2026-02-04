package com.nvda.screenreader.ocr

import android.content.Context
import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.arabic.TextRecognizerOptions
import java.util.Locale

/**
 * خدمة OCR للتعرف على النصوص من الصور
 * تستخدم Google ML Kit
 */
class OCRService(
    private val context: Context,
    private val textToSpeech: TextToSpeech
) {

    private val recognizer = TextRecognition.getClient(
        TextRecognizerOptions.Builder()
            .setLanguage("ar")
            .build()
    )

    /**
     * التعرف على النصوص في صورة
     */
    fun recognizeText(bitmap: Bitmap, onResult: (String) -> Unit, onError: (Exception) -> Unit) {
        try {
            val image = InputImage.fromBitmap(bitmap, 0)
            
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val recognizedText = visionText.text
                    onResult(recognizedText)
                    
                    // قراءة النص المكتشف
                    if (recognizedText.isNotEmpty()) {
                        speak(recognizedText)
                    }
                }
                .addOnFailureListener { e ->
                    onError(e)
                }
        } catch (e: Exception) {
            onError(e)
        }
    }

    /**
     * التعرف على النصوص في صورة مع معالجة متقدمة
     */
    fun recognizeTextAdvanced(
        bitmap: Bitmap,
        onResult: (OCRResult) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            val image = InputImage.fromBitmap(bitmap, 0)
            
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val result = OCRResult(
                        fullText = visionText.text,
                        blocks = visionText.textBlocks.map { block ->
                            OCRBlock(
                                text = block.text,
                                confidence = block.confidence,
                                lines = block.lines.map { line ->
                                    OCRLine(
                                        text = line.text,
                                        confidence = line.confidence,
                                        elements = line.elements.map { element ->
                                            OCRElement(
                                                text = element.text,
                                                confidence = element.confidence
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                    
                    onResult(result)
                    
                    // قراءة النص المكتشف
                    if (result.fullText.isNotEmpty()) {
                        speak(result.fullText)
                    }
                }
                .addOnFailureListener { e ->
                    onError(e)
                }
        } catch (e: Exception) {
            onError(e)
        }
    }

    /**
     * قراءة نص باستخدام Text-to-Speech
     */
    private fun speak(text: String) {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    /**
     * إيقاف الخدمة
     */
    fun stop() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
    }

    /**
     * تحرير الموارد
     */
    fun release() {
        recognizer.close()
    }
}

/**
 * نتيجة OCR الكاملة
 */
data class OCRResult(
    val fullText: String,
    val blocks: List<OCRBlock>
)

/**
 * كتلة نص
 */
data class OCRBlock(
    val text: String,
    val confidence: Float,
    val lines: List<OCRLine>
)

/**
 * سطر نص
 */
data class OCRLine(
    val text: String,
    val confidence: Float,
    val elements: List<OCRElement>
)

/**
 * عنصر نص
 */
data class OCRElement(
    val text: String,
    val confidence: Float
)
