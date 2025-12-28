/*
 * QR & Barcode Scanner
 * Based on QrCraft by K M Rejowan Ahmmed
 * https://github.com/ahmmedrejowan/QrCraft
 *
 * Original Copyright (C) 2025 K M Rejowan Ahmmed
 * Modifications Copyright (C) 2025 Appease
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.appsease.qrbarcode.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsease.qrbarcode.data.local.preferences.ThemePreferences
import com.appsease.qrbarcode.domain.repository.GeneratorRepository
import com.appsease.qrbarcode.domain.repository.ScanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val scanRepository: ScanRepository,
    private val generatorRepository: GeneratorRepository,
    private val themePreferences: ThemePreferences
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            try {
                val scanCount = scanRepository.getCount()
                val generatedCount = generatorRepository.getCount()
                _state.value = _state.value.copy(
                    scanCount = scanCount,
                    generatedCount = generatedCount,
                    totalCount = scanCount + generatedCount
                )
            } catch (e: Exception) {
                // Silently fail for stats
            }
        }
    }

    fun clearScanHistory() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                scanRepository.deleteAll()
                loadStats() // Refresh stats
                _state.value = _state.value.copy(
                    isLoading = false,
                    successMessage = "Scan history cleared"
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to clear scan history: ${e.message}"
                )
            }
        }
    }

    fun clearGeneratedHistory() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                generatorRepository.deleteAll()
                loadStats() // Refresh stats
                _state.value = _state.value.copy(
                    isLoading = false,
                    successMessage = "Generated codes cleared"
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to clear generated history: ${e.message}"
                )
            }
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                scanRepository.deleteAll()
                generatorRepository.deleteAll()
                // Reset preferences to defaults
                themePreferences.setTheme("System")
                themePreferences.setDynamicColor(false)
                loadStats() // Refresh stats
                _state.value = _state.value.copy(
                    isLoading = false,
                    successMessage = "All data cleared"
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to clear all data: ${e.message}"
                )
            }
        }
    }

    fun clearMessages() {
        _state.value = _state.value.copy(successMessage = null, error = null)
    }
}

data class SettingsState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val error: String? = null,
    val scanCount: Int = 0,
    val generatedCount: Int = 0,
    val totalCount: Int = 0
)
