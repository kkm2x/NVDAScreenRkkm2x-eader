# ProGuard rules for NVDA Screen Reader

# Keep all classes in our package
-keep class com.nvda.screenreader.** { *; }

# Keep Android framework classes
-keep class android.** { *; }
-keep interface android.** { *; }

# Keep accessibility classes
-keep class android.accessibilityservice.** { *; }
-keep interface android.accessibilityservice.** { *; }

# Keep Text-to-Speech classes
-keep class android.speech.tts.** { *; }
-keep interface android.speech.tts.** { *; }

# Keep Google ML Kit classes
-keep class com.google.mlkit.** { *; }
-keep interface com.google.mlkit.** { *; }

# Keep Gson classes
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }

# Keep Kotlin classes
-keep class kotlin.** { *; }
-keep interface kotlin.** { *; }

# Remove logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Optimize
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose

# Remove unused code
-dontshrink
-dontoptimize
-dontobfuscate

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable classes
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep R classes
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep view constructors for inflation
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

# Keep onClick handlers
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# Keep Fragment classes
-keep class * extends android.app.Fragment
-keep class * extends androidx.fragment.app.Fragment

# Keep Service classes
-keep class * extends android.app.Service
-keep class * extends android.accessibilityservice.AccessibilityService

# Keep BroadcastReceiver classes
-keep class * extends android.content.BroadcastReceiver

# Keep ContentProvider classes
-keep class * extends android.content.ContentProvider

# Keep Application classes
-keep class * extends android.app.Application

# Keep custom application classes
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class * extends android.widget.BaseAdapter
-keep public class * extends android.widget.ListAdapter
-keep public class * extends android.widget.SpinnerAdapter
