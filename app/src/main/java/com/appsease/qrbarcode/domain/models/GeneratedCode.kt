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

package com.appsease.qrbarcode.domain.models

import android.graphics.Bitmap

data class GeneratedCode(
    val content: String,
    val format: BarcodeFormat,
    val contentType: ContentType,
    val bitmap: Bitmap?,
    val customization: CodeCustomization,
    val timestamp: Long = System.currentTimeMillis()
)

data class CodeCustomization(
    val size: Int = 512,
    val foregroundColor: Int = android.graphics.Color.BLACK,
    val backgroundColor: Int = android.graphics.Color.WHITE,
    val errorCorrectionLevel: ErrorCorrectionLevel = ErrorCorrectionLevel.MEDIUM,
    val margin: Int = 1
)

enum class ErrorCorrectionLevel(val displayName: String, val zxingLevel: com.google.zxing.qrcode.decoder.ErrorCorrectionLevel) {
    LOW("Low (~7%)", com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.L),
    MEDIUM("Medium (~15%)", com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.M),
    QUARTILE("Quartile (~25%)", com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.Q),
    HIGH("High (~30%)", com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H)
}
