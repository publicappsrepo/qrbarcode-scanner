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

package com.appsease.qrbarcode.presentation.scanner.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsease.qrbarcode.domain.models.BarcodeFormat
import com.appsease.qrbarcode.domain.models.ContentType
import com.appsease.qrbarcode.domain.models.ScanResult
import com.appsease.qrbarcode.domain.repository.ScanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

data class ScanHistoryDetailState(
    val scanResult: ScanResult? = null,
    val scanId: Long? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val showDeleteDialog: Boolean = false
)

class ScanHistoryDetailViewModel(
    private val scanRepository: ScanRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ScanHistoryDetailState())
    val state: StateFlow<ScanHistoryDetailState> = _state.asStateFlow()

    fun loadScan(scanId: Long) {
        viewModelScope.launch {
            _state.value = ScanHistoryDetailState(isLoading = true, scanId = scanId)

            try {
                val history = scanRepository.getHistoryById(scanId)
                if (history != null) {
                    // Convert history to ScanResult
                    val scanResult = ScanResult(
                        rawValue = history.content,
                        displayValue = history.content,
                        format = history.format,
                        contentType = history.contentType,
                        timestamp = history.timestamp,
                        metadata = history.metadata
                    )

                    _state.value = ScanHistoryDetailState(
                        scanResult = scanResult,
                        scanId = scanId,
                        isLoading = false
                    )
                } else {
                    _state.value = ScanHistoryDetailState(
                        isLoading = false,
                        error = "Scan not found"
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to load scan")
                _state.value = ScanHistoryDetailState(
                    isLoading = false,
                    error = "Failed to load scan: ${e.message}"
                )
            }
        }
    }

    fun showDeleteDialog() {
        _state.value = _state.value.copy(showDeleteDialog = true)
    }

    fun hideDeleteDialog() {
        _state.value = _state.value.copy(showDeleteDialog = false)
    }

    suspend fun deleteScan(): Boolean {
        return try {
            val scanId = _state.value.scanId ?: return false
            val entity = scanRepository.getHistoryById(scanId)
            if (entity != null) {
                scanRepository.deleteScan(entity)
                Timber.d("Scan deleted successfully")
                true
            } else {
                Timber.e("Scan not found for deletion")
                false
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to delete scan")
            false
        }
    }
}
