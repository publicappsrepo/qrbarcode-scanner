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

package com.appsease.qrbarcode.presentation.scanner.state

import com.appsease.qrbarcode.domain.models.ScanResult

/**
 * Represents the overall scanner screen state
 */
data class ScannerScreenState(
    val isPreviewActive: Boolean = true, // Camera preview on/off
    val scanningState: ScanningState = ScanningState.Scanning
)

/**
 * Represents the scanning operation state
 */
sealed interface ScanningState {
    data object Idle : ScanningState
    data object Scanning : ScanningState
    data class Success(val result: ScanResult) : ScanningState
    data class Error(val message: String) : ScanningState
}

sealed interface ScannerEvent {
    data object StartScanning : ScannerEvent
    data object StopScanning : ScannerEvent
    data class OnBarcodeDetected(val result: ScanResult) : ScannerEvent
    data object OnResultDismissed : ScannerEvent
    data object OnSaveToHistory : ScannerEvent
    data class OnError(val message: String) : ScannerEvent

    // Preview control events
    data object TogglePreview : ScannerEvent
    data object StartPreview : ScannerEvent
    data object StopPreview : ScannerEvent
}
