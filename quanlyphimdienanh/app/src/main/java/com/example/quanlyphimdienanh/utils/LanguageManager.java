package com.example.quanlyphimdienanh.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LanguageManager {
    private static final String LANGUAGE_KEY = "language";
    private static final String VIETNAMESE = "vi";
    private static final String ENGLISH = "en";

    private final SharedPreferences preferences;
    private final Context context;

    public LanguageManager(Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLanguage(String languageCode) {
        preferences.edit().putString(LANGUAGE_KEY, languageCode).apply();
        updateResources(languageCode);
    }

    public String getCurrentLanguage() {
        return preferences.getString(LANGUAGE_KEY, VIETNAMESE); // Mặc định là tiếng Việt
    }

    public void toggleLanguage() {
        String currentLanguage = getCurrentLanguage();
        String newLanguage = currentLanguage.equals(VIETNAMESE) ? ENGLISH : VIETNAMESE;
        setLanguage(newLanguage);
    }

    public void updateResources(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
} 