package com.yucatancorp.ecommercemarcosnarvaez.domain

import android.content.Context
import androidx.core.content.edit

class SearchHistoryManager(
    context: Context
) {

    private val preferences = context.getSharedPreferences("search_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_SEARCH_HISTORY = "search_history"
        private const val MAX_HISTORY_SIZE = 10
    }

    fun saveSearch(query: String) {
        val cleanQuery = query.trim()
        if (cleanQuery.isEmpty())
            return

        val currentHistory = getSearchHistory().toMutableList()
        currentHistory.remove(cleanQuery)
        currentHistory.add(0, cleanQuery)

        val limitedHistory = currentHistory.take(MAX_HISTORY_SIZE)
        preferences.edit { putStringSet(KEY_SEARCH_HISTORY, limitedHistory.toSet()) }
    }

    fun getSearchHistory(): List<String> {
        return preferences
            .getStringSet(KEY_SEARCH_HISTORY, emptySet())
            ?.toList()
            ?: emptyList()
    }

    fun clearHistory() {
        preferences.edit { remove(KEY_SEARCH_HISTORY) }
    }
}