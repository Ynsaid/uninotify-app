import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import java.util.Locale

object LanguagesPreferences {
    private const val PREF_NAME = "app_prefs"
    private const val KEY_LANGUAGE = "language"

    fun saveLanguage(context: Context, langCode: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString(KEY_LANGUAGE, langCode).apply()
    }

    fun getSavedLanguageCode(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_LANGUAGE, "en") ?: "en"
    }

    fun getSavedLanguageName(context: Context): String {
        return when (getSavedLanguageCode(context)) {
            "en" -> "English"
            "fr" -> "French"
            "ar" -> "Arabic"
            "es" -> "Spanish"
            else -> "English"
        }
    }

    fun setLocale(context: Context, langCode: String) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun restartApp(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        if (context is Activity) {
            context.startActivity(intent)
            context.finish()
        }
    }
}