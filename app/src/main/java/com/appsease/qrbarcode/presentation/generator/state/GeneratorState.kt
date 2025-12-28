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

package com.appsease.qrbarcode.presentation.generator.state

import com.appsease.qrbarcode.domain.models.BarcodeFormat
import com.appsease.qrbarcode.domain.models.BarcodeType
import com.appsease.qrbarcode.domain.models.CodeCustomization
import com.appsease.qrbarcode.domain.models.ContentType
import com.appsease.qrbarcode.domain.models.GeneratedCode

data class GeneratorState(
    val selectedContentType: ContentType = ContentType.TEXT,
    val selectedBarcodeType: BarcodeType = BarcodeType.TWO_D,
    val selectedBarcodeFormat: BarcodeFormat = BarcodeFormat.QR_CODE,
    val inputContent: String = "",
    val customization: CodeCustomization = CodeCustomization(),
    val generatedCode: GeneratedCode? = null,
    val isGenerating: Boolean = false,
    val error: String? = null,
    val showCustomization: Boolean = false,
    val realTimePreview: Boolean = true
)

sealed interface GeneratorEvent {
    data class OnContentTypeSelected(val type: ContentType) : GeneratorEvent
    data class OnBarcodeTypeSelected(val type: BarcodeType) : GeneratorEvent
    data class OnBarcodeFormatSelected(val format: BarcodeFormat) : GeneratorEvent
    data class OnInputChanged(val input: String) : GeneratorEvent
    data object OnGenerateClicked : GeneratorEvent
    data class OnCustomizationChanged(val customization: CodeCustomization) : GeneratorEvent
    data object OnToggleCustomization : GeneratorEvent
    data object OnSaveClicked : GeneratorEvent
    data object OnShareClicked : GeneratorEvent
    data object OnClearClicked : GeneratorEvent
}
