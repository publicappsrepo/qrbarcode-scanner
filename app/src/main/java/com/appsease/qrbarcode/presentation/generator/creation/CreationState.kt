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

package com.appsease.qrbarcode.presentation.generator.creation

import android.graphics.Bitmap
import com.appsease.qrbarcode.domain.models.BarcodeFormat
import com.appsease.qrbarcode.domain.models.CodeCustomization
import com.appsease.qrbarcode.domain.models.Template

data class CreationState(
    val template: Template? = null,
    val fieldValues: Map<String, String> = emptyMap(),
    val title: String = "",
    val note: String = "",
    val selectedFormat: BarcodeFormat? = null,
    val customization: CodeCustomization = CodeCustomization(),
    val generatedBitmap: Bitmap? = null,
    val isGenerating: Boolean = false,
    val isSaving: Boolean = false,
    val isSharing: Boolean = false,
    val error: String? = null,
    val validationErrors: Map<String, String> = emptyMap(),
    val showFormatSheet: Boolean = false,
    val showCustomizationSheet: Boolean = false,
    val isLoading: Boolean = true,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val showExitDialog: Boolean = false,
    val editingCodeId: Long? = null, // For edit mode
    val isEditMode: Boolean = false,
    // Capacity tracking
    val contentLength: Int = 0,
    val maxCapacity: Int = 0,
    val capacityWarning: String? = null,
    val capacityPercentage: Float = 0f
) {
    fun hasUnsavedChanges(): Boolean {
        return fieldValues.values.any { it.isNotBlank() } ||
                title.isNotBlank() ||
                note.isNotBlank() ||
                generatedBitmap != null
    }
}
