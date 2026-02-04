package com.nvda.screenreader.ui

import android.os.Bundle
import android.widget.SeekBar
import android.widget.Switch
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.nvda.screenreader.R
import com.nvda.screenreader.settings.NVDASettings

/**
 * نشاط التفضيلات
 * يحتوي على جميع إعدادات NVDA
 */
class PreferencesActivity : AppCompatActivity() {

    private lateinit var settings: NVDASettings
    
    // Speech Settings
    private lateinit var synthesiserSpinner: Spinner
    private lateinit var voiceSpinner: Spinner
    private lateinit var speechRateSeekBar: SeekBar
    private lateinit var volumeSeekBar: SeekBar
    private lateinit var pitchSeekBar: SeekBar
    private lateinit var inflectionSeekBar: SeekBar
    private lateinit var punctuationLevelSpinner: Spinner
    private lateinit var announceCapitalsSwitch: Switch
    private lateinit var sayNumbersSwitch: Switch
    
    // Speech Mode
    private lateinit var speechModeSpinner: Spinner
    private lateinit var reviewModeSpinner: Spinner
    private lateinit var navigationModeSpinner: Spinner
    
    // Announcements
    private lateinit var autoReadSwitch: Switch
    private lateinit var announceScreenChangesSwitch: Switch
    private lateinit var announceFormFieldsSwitch: Switch
    private lateinit var announceLinksSwitch: Switch
    private lateinit var announceGraphicsSwitch: Switch
    private lateinit var announceButtonsSwitch: Switch
    private lateinit var announceMenusSwitch: Switch
    private lateinit var announceTablesSwitch: Switch
    private lateinit var announceFramesSwitch: Switch
    private lateinit var announcePageStructureSwitch: Switch
    private lateinit var announceEmphasisSwitch: Switch
    
    // Vision Settings
    private lateinit var visualHighlightSwitch: Switch
    private lateinit var focusHighlightSwitch: Switch
    private lateinit var caretHighlightSwitch: Switch
    private lateinit var highlightThicknessSeekBar: SeekBar
    
    // Touch Settings
    private lateinit var touchTypingModeSwitch: Switch
    private lateinit var touchKeyboardSwitch: Switch
    private lateinit var tapToActivateSwitch: Switch
    private lateinit var flickSensitivitySeekBar: SeekBar
    
    // Keyboard Settings
    private lateinit var nVDAModifierKeySpinner: Spinner
    private lateinit var keyboardLayoutSpinner: Spinner
    private lateinit var enableKeyBindingsSwitch: Switch
    
    // Braille Settings
    private lateinit var brailleEnabledSwitch: Switch
    private lateinit var brailleTableSpinner: Spinner
    
    // Advanced Settings
    private lateinit var ocrEnabledSwitch: Switch
    private lateinit var ocrLanguageSpinner: Spinner
    private lateinit var enableLoggingSwitch: Switch
    private lateinit var performanceModeSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        
        settings = NVDASettings(this)
        
        initializeViews()
        loadSettings()
        setupListeners()
    }

    private fun initializeViews() {
        // Speech Settings
        synthesiserSpinner = findViewById(R.id.synthesiser_spinner)
        voiceSpinner = findViewById(R.id.voice_spinner)
        speechRateSeekBar = findViewById(R.id.speech_rate_seekbar)
        volumeSeekBar = findViewById(R.id.volume_seekbar)
        pitchSeekBar = findViewById(R.id.pitch_seekbar)
        inflectionSeekBar = findViewById(R.id.inflection_seekbar)
        punctuationLevelSpinner = findViewById(R.id.punctuation_level_spinner)
        announceCapitalsSwitch = findViewById(R.id.announce_capitals_switch)
        sayNumbersSwitch = findViewById(R.id.say_numbers_switch)
        
        // Speech Mode
        speechModeSpinner = findViewById(R.id.speech_mode_spinner)
        reviewModeSpinner = findViewById(R.id.review_mode_spinner)
        navigationModeSpinner = findViewById(R.id.navigation_mode_spinner)
        
        // Announcements
        autoReadSwitch = findViewById(R.id.auto_read_switch)
        announceScreenChangesSwitch = findViewById(R.id.announce_screen_changes_switch)
        announceFormFieldsSwitch = findViewById(R.id.announce_form_fields_switch)
        announceLinksSwitch = findViewById(R.id.announce_links_switch)
        announceGraphicsSwitch = findViewById(R.id.announce_graphics_switch)
        announceButtonsSwitch = findViewById(R.id.announce_buttons_switch)
        announceMenusSwitch = findViewById(R.id.announce_menus_switch)
        announceTablesSwitch = findViewById(R.id.announce_tables_switch)
        announceFramesSwitch = findViewById(R.id.announce_frames_switch)
        announcePageStructureSwitch = findViewById(R.id.announce_page_structure_switch)
        announceEmphasisSwitch = findViewById(R.id.announce_emphasis_switch)
        
        // Vision Settings
        visualHighlightSwitch = findViewById(R.id.visual_highlight_switch)
        focusHighlightSwitch = findViewById(R.id.focus_highlight_switch)
        caretHighlightSwitch = findViewById(R.id.caret_highlight_switch)
        highlightThicknessSeekBar = findViewById(R.id.highlight_thickness_seekbar)
        
        // Touch Settings
        touchTypingModeSwitch = findViewById(R.id.touch_typing_mode_switch)
        touchKeyboardSwitch = findViewById(R.id.touch_keyboard_switch)
        tapToActivateSwitch = findViewById(R.id.tap_to_activate_switch)
        flickSensitivitySeekBar = findViewById(R.id.flick_sensitivity_seekbar)
        
        // Keyboard Settings
        nVDAModifierKeySpinner = findViewById(R.id.nvda_modifier_key_spinner)
        keyboardLayoutSpinner = findViewById(R.id.keyboard_layout_spinner)
        enableKeyBindingsSwitch = findViewById(R.id.enable_key_bindings_switch)
        
        // Braille Settings
        brailleEnabledSwitch = findViewById(R.id.braille_enabled_switch)
        brailleTableSpinner = findViewById(R.id.braille_table_spinner)
        
        // Advanced Settings
        ocrEnabledSwitch = findViewById(R.id.ocr_enabled_switch)
        ocrLanguageSpinner = findViewById(R.id.ocr_language_spinner)
        enableLoggingSwitch = findViewById(R.id.enable_logging_switch)
        performanceModeSwitch = findViewById(R.id.performance_mode_switch)
        
        // Setup Spinners
        setupSpinners()
    }

    private fun setupSpinners() {
        // Synthesiser
        val synthesiserAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.synthesiser_array,
            android.R.layout.simple_spinner_item
        )
        synthesiserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        synthesiserSpinner.adapter = synthesiserAdapter
        
        // Voice
        val voiceAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.voice_array,
            android.R.layout.simple_spinner_item
        )
        voiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        voiceSpinner.adapter = voiceAdapter
        
        // Speech Mode
        val speechModeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.speech_mode_array,
            android.R.layout.simple_spinner_item
        )
        speechModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        speechModeSpinner.adapter = speechModeAdapter
        
        // Review Mode
        val reviewModeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.review_mode_array,
            android.R.layout.simple_spinner_item
        )
        reviewModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        reviewModeSpinner.adapter = reviewModeAdapter
        
        // Navigation Mode
        val navigationModeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.navigation_mode_array,
            android.R.layout.simple_spinner_item
        )
        navigationModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        navigationModeSpinner.adapter = navigationModeAdapter
        
        // Punctuation Level
        val punctuationLevelAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.punctuation_level_array,
            android.R.layout.simple_spinner_item
        )
        punctuationLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        punctuationLevelSpinner.adapter = punctuationLevelAdapter
        
        // NVDA Modifier Key
        val nVDAModifierKeyAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.nvda_modifier_key_array,
            android.R.layout.simple_spinner_item
        )
        nVDAModifierKeyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nVDAModifierKeySpinner.adapter = nVDAModifierKeyAdapter
        
        // Keyboard Layout
        val keyboardLayoutAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.keyboard_layout_array,
            android.R.layout.simple_spinner_item
        )
        keyboardLayoutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        keyboardLayoutSpinner.adapter = keyboardLayoutAdapter
        
        // Braille Table
        val brailleTableAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.braille_table_array,
            android.R.layout.simple_spinner_item
        )
        brailleTableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        brailleTableSpinner.adapter = brailleTableAdapter
        
        // OCR Language
        val ocrLanguageAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.ocr_language_array,
            android.R.layout.simple_spinner_item
        )
        ocrLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ocrLanguageSpinner.adapter = ocrLanguageAdapter
    }

    private fun loadSettings() {
        // Load Speech Settings
        speechRateSeekBar.progress = (settings.speechRate * 50).toInt()
        volumeSeekBar.progress = (settings.volume * 100).toInt()
        pitchSeekBar.progress = (settings.pitch * 50).toInt()
        inflectionSeekBar.progress = settings.inflection
        announceCapitalsSwitch.isChecked = settings.announceCapitals
        sayNumbersSwitch.isChecked = settings.sayNumbers
        
        // Load Speech Mode
        autoReadSwitch.isChecked = settings.autoRead
        announceScreenChangesSwitch.isChecked = settings.announceScreenChanges
        announceFormFieldsSwitch.isChecked = settings.announceFormFields
        announceLinksSwitch.isChecked = settings.announceLinks
        announceGraphicsSwitch.isChecked = settings.announceGraphics
        announceButtonsSwitch.isChecked = settings.announceButtons
        announceMenusSwitch.isChecked = settings.announceMenus
        announceTablesSwitch.isChecked = settings.announceTables
        announceFramesSwitch.isChecked = settings.announceFrames
        announcePageStructureSwitch.isChecked = settings.announcePageStructure
        announceEmphasisSwitch.isChecked = settings.announceEmphasis
        
        // Load Vision Settings
        visualHighlightSwitch.isChecked = settings.visualHighlight
        focusHighlightSwitch.isChecked = settings.focusHighlight
        caretHighlightSwitch.isChecked = settings.caretHighlight
        highlightThicknessSeekBar.progress = settings.highlightThickness
        
        // Load Touch Settings
        touchTypingModeSwitch.isChecked = settings.touchTypingMode
        touchKeyboardSwitch.isChecked = settings.touchKeyboard
        tapToActivateSwitch.isChecked = settings.tapToActivate
        flickSensitivitySeekBar.progress = settings.flickSensitivity
        
        // Load Keyboard Settings
        enableKeyBindingsSwitch.isChecked = settings.enableKeyBindings
        
        // Load Braille Settings
        brailleEnabledSwitch.isChecked = settings.brailleEnabled
        
        // Load Advanced Settings
        ocrEnabledSwitch.isChecked = settings.ocrEnabled
        enableLoggingSwitch.isChecked = settings.enableLogging
        performanceModeSwitch.isChecked = settings.performanceMode
    }

    private fun setupListeners() {
        // Speech Settings
        speechRateSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                settings.speechRate = progress / 50.0f
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                settings.volume = progress / 100.0f
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        pitchSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                settings.pitch = progress / 50.0f
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        inflectionSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                settings.inflection = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        announceCapitalsSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceCapitals = isChecked
        }
        
        sayNumbersSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.sayNumbers = isChecked
        }
        
        // Announcement Switches
        autoReadSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.autoRead = isChecked
        }
        
        announceScreenChangesSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceScreenChanges = isChecked
        }
        
        announceFormFieldsSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceFormFields = isChecked
        }
        
        announceLinksSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceLinks = isChecked
        }
        
        announceGraphicsSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceGraphics = isChecked
        }
        
        announceButtonsSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceButtons = isChecked
        }
        
        announceMenusSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceMenus = isChecked
        }
        
        announceTablesSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceTables = isChecked
        }
        
        announceFramesSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceFrames = isChecked
        }
        
        announcePageStructureSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announcePageStructure = isChecked
        }
        
        announceEmphasisSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.announceEmphasis = isChecked
        }
        
        // Vision Settings
        visualHighlightSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.visualHighlight = isChecked
        }
        
        focusHighlightSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.focusHighlight = isChecked
        }
        
        caretHighlightSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.caretHighlight = isChecked
        }
        
        highlightThicknessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                settings.highlightThickness = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // Touch Settings
        touchTypingModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.touchTypingMode = isChecked
        }
        
        touchKeyboardSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.touchKeyboard = isChecked
        }
        
        tapToActivateSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.tapToActivate = isChecked
        }
        
        flickSensitivitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                settings.flickSensitivity = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // Keyboard Settings
        enableKeyBindingsSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.enableKeyBindings = isChecked
        }
        
        // Braille Settings
        brailleEnabledSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.brailleEnabled = isChecked
        }
        
        // Advanced Settings
        ocrEnabledSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.ocrEnabled = isChecked
        }
        
        enableLoggingSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.enableLogging = isChecked
        }
        
        performanceModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.performanceMode = isChecked
        }
    }
}
