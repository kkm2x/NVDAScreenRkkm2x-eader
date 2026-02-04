package com.nvda.screenreader.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nvda.screenreader.R
import com.nvda.screenreader.ocr.OCRService
import android.speech.tts.TextToSpeech
import java.util.Locale

/**
 * نشاط OCR
 * يقوم بالتعرف على النصوص من الصور
 */
class OCRActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var imageView: ImageView
    private lateinit var resultText: TextView
    private lateinit var cameraButton: Button
    private lateinit var galleryButton: Button
    private lateinit var progressBar: ProgressBar
    
    private lateinit var ocrService: OCRService
    private lateinit var textToSpeech: TextToSpeech
    
    private val CAMERA_PERMISSION_CODE = 100
    private val GALLERY_PERMISSION_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    private val GALLERY_REQUEST_CODE = 103

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr)
        
        // تهيئة Text-to-Speech
        textToSpeech = TextToSpeech(this, this)
        
        // تهيئة OCR Service
        ocrService = OCRService(this, textToSpeech)
        
        // تهيئة العناصر
        imageView = findViewById(R.id.image_view)
        resultText = findViewById(R.id.result_text)
        cameraButton = findViewById(R.id.camera_button)
        galleryButton = findViewById(R.id.gallery_button)
        progressBar = findViewById(R.id.progress_bar)
        
        // تعيين المستمعين
        cameraButton.setOnClickListener { openCamera() }
        galleryButton.setOnClickListener { openGallery() }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
    }

    private fun openGallery() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_PERMISSION_CODE
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data.extras?.get("data") as? Bitmap
                    if (bitmap != null) {
                        processImage(bitmap)
                    }
                }
                GALLERY_REQUEST_CODE -> {
                    val uri = data.data
                    if (uri != null) {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        processImage(bitmap)
                    }
                }
            }
        }
    }

    private fun processImage(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
        progressBar.visibility = android.view.View.VISIBLE
        resultText.text = "جاري معالجة الصورة..."
        
        ocrService.recognizeTextAdvanced(
            bitmap,
            { result ->
                progressBar.visibility = android.view.View.GONE
                resultText.text = result.fullText
                speak("تم التعرف على النص: ${result.fullText}")
            },
            { error ->
                progressBar.visibility = android.view.View.GONE
                resultText.text = "خطأ: ${error.message}"
                speak("حدث خطأ في معالجة الصورة")
                Toast.makeText(this, "خطأ: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun speak(text: String) {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, "تم رفض إذن الكاميرا", Toast.LENGTH_SHORT).show()
                }
            }
            GALLERY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(this, "تم رفض إذن الوصول للملفات", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("ar", "SA"))
            
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech.setLanguage(Locale.getDefault())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ocrService.release()
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
    }
}
