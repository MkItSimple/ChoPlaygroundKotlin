package com.example.choplaygroundkotlin.framework.datasource.preferences

import com.example.choplaygroundkotlin.framework.datasource.preferences.PreferenceKeys.Companion.FOLDER_PREFERENCES

class PreferenceKeys {

    companion object{

        // Shared Preference Files:
        const val FOLDER_PREFERENCES: String = "com.example.choplaygroundkotlin.notes"

        // Shared Preference Keys
        val FOLDER_FILTER: String = "${FOLDER_PREFERENCES}.FOLDER_FILTER"
        val FOLDER_ORDER: String = "${FOLDER_PREFERENCES}.FOLDER_ORDER"
    }
}