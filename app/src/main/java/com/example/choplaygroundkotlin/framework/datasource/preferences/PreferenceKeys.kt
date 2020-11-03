package com.example.choplaygroundkotlin.framework.datasource.preferences

import com.example.choplaygroundkotlin.framework.datasource.preferences.PreferenceKeys.Companion.NOTE_PREFERENCES

class PreferenceKeys {

    companion object{

        // Shared Preference Files:
        const val NOTE_PREFERENCES: String = "com.example.choplaygroundkotlin.notes"

        // Shared Preference Keys
        val FOLDER_FILTER: String = "${NOTE_PREFERENCES}.FOLDER_FILTER"
        val FOLDER_ORDER: String = "${NOTE_PREFERENCES}.FOLDER_ORDER"
    }
}