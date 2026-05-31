package com.example.ungdungthoitiet.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cai_dat_thoi_tiet")

class QuanLyCaiDat(private val context: Context) {

    companion object {
        private val KEY_DON_VI_NHIET_DO = stringPreferencesKey("don_vi_nhiet_do")
        private val KEY_DON_VI_GIO = stringPreferencesKey("don_vi_gio")
    }

    val donViNhietDo: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_DON_VI_NHIET_DO] ?: "°C"
    }

    val donViGio: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_DON_VI_GIO] ?: "km/h"
    }

    suspend fun luuDonViNhietDo(donVi: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_DON_VI_NHIET_DO] = donVi
        }
    }

    suspend fun luuDonViGio(donVi: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_DON_VI_GIO] = donVi
        }
    }
}
