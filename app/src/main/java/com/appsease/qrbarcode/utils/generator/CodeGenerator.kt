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

package com.appsease.qrbarcode.utils.generator

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.appsease.qrbarcode.domain.models.CodeCustomization
import timber.log.Timber

object CodeGenerator {

    fun generateQRCode(
        content: String,
        customization: CodeCustomization = CodeCustomization()
    ): Bitmap? {
        return generateCode(
            content = content,
            format = BarcodeFormat.QR_CODE,
            customization = customization
        )
    }

    fun generateCode(
        content: String,
        format: com.appsease.qrbarcode.domain.models.BarcodeFormat,
        customization: CodeCustomization = CodeCustomization()
    ): Bitmap? {
        val zxingFormat = mapToZXingFormat(format)
        return generateCode(content, zxingFormat, customization)
    }

    private fun generateCode(
        content: String,
        format: BarcodeFormat,
        customization: CodeCustomization = CodeCustomization()
    ): Bitmap? {
        if (content.isEmpty()) {
            Timber.w("Cannot generate code with empty content")
            return null
        }

        return try {
            val hints = mutableMapOf<EncodeHintType, Any>()
            hints[EncodeHintType.MARGIN] = customization.margin

            // Error correction only applies to QR codes
            if (format == BarcodeFormat.QR_CODE) {
                hints[EncodeHintType.ERROR_CORRECTION] = when (customization.errorCorrectionLevel) {
                    com.appsease.qrbarcode.domain.models.ErrorCorrectionLevel.LOW -> ErrorCorrectionLevel.L
                    com.appsease.qrbarcode.domain.models.ErrorCorrectionLevel.MEDIUM -> ErrorCorrectionLevel.M
                    com.appsease.qrbarcode.domain.models.ErrorCorrectionLevel.QUARTILE -> ErrorCorrectionLevel.Q
                    com.appsease.qrbarcode.domain.models.ErrorCorrectionLevel.HIGH -> ErrorCorrectionLevel.H
                }
            }

            val writer = MultiFormatWriter()
            val bitMatrix = writer.encode(
                content,
                format,
                customization.size,
                customization.size,
                hints
            )

            createBitmap(bitMatrix, customization)
        } catch (e: WriterException) {
            Timber.e(e, "Failed to generate code")
            null
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error during code generation")
            null
        }
    }

    private fun createBitmap(
        bitMatrix: BitMatrix,
        customization: CodeCustomization
    ): Bitmap {
        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)

        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (bitMatrix[x, y]) {
                    customization.foregroundColor
                } else {
                    customization.backgroundColor
                }
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    fun formatContentForType(
        content: String,
        contentType: com.appsease.qrbarcode.domain.models.ContentType
    ): String {
        return when (contentType) {
            com.appsease.qrbarcode.domain.models.ContentType.URL -> {
                if (!content.startsWith("http://") && !content.startsWith("https://")) {
                    "https://$content"
                } else {
                    content
                }
            }
            com.appsease.qrbarcode.domain.models.ContentType.EMAIL -> {
                if (!content.startsWith("mailto:")) {
                    "mailto:$content"
                } else {
                    content
                }
            }
            com.appsease.qrbarcode.domain.models.ContentType.PHONE -> {
                if (!content.startsWith("tel:")) {
                    "tel:$content"
                } else {
                    content
                }
            }
            com.appsease.qrbarcode.domain.models.ContentType.SMS -> {
                if (!content.startsWith("smsto:")) {
                    "smsto:$content"
                } else {
                    content
                }
            }
            else -> content
        }
    }

    fun createWiFiString(ssid: String, password: String, encryption: String = "WPA"): String {
        return "WIFI:T:$encryption;S:$ssid;P:$password;;"
    }

    fun createVCard(
        name: String,
        phone: String? = null,
        email: String? = null,
        organization: String? = null,
        website: String? = null
    ): String {
        return buildString {
            appendLine("BEGIN:VCARD")
            appendLine("VERSION:3.0")
            appendLine("FN:$name")
            phone?.let { appendLine("TEL:$it") }
            email?.let { appendLine("EMAIL:$it") }
            organization?.let { appendLine("ORG:$it") }
            website?.let { appendLine("URL:$it") }
            append("END:VCARD")
        }
    }

    fun createGeoLocation(latitude: Double, longitude: Double): String {
        return "geo:$latitude,$longitude"
    }

    private fun mapToZXingFormat(format: com.appsease.qrbarcode.domain.models.BarcodeFormat): BarcodeFormat {
        return when (format) {
            com.appsease.qrbarcode.domain.models.BarcodeFormat.QR_CODE -> BarcodeFormat.QR_CODE
            com.appsease.qrbarcode.domain.models.BarcodeFormat.AZTEC -> BarcodeFormat.AZTEC
            com.appsease.qrbarcode.domain.models.BarcodeFormat.DATA_MATRIX -> BarcodeFormat.DATA_MATRIX
            com.appsease.qrbarcode.domain.models.BarcodeFormat.PDF417 -> BarcodeFormat.PDF_417
            com.appsease.qrbarcode.domain.models.BarcodeFormat.EAN_8 -> BarcodeFormat.EAN_8
            com.appsease.qrbarcode.domain.models.BarcodeFormat.EAN_13 -> BarcodeFormat.EAN_13
            com.appsease.qrbarcode.domain.models.BarcodeFormat.UPC_A -> BarcodeFormat.UPC_A
            com.appsease.qrbarcode.domain.models.BarcodeFormat.UPC_E -> BarcodeFormat.UPC_E
            com.appsease.qrbarcode.domain.models.BarcodeFormat.CODE_39 -> BarcodeFormat.CODE_39
            com.appsease.qrbarcode.domain.models.BarcodeFormat.CODE_93 -> BarcodeFormat.CODE_93
            com.appsease.qrbarcode.domain.models.BarcodeFormat.CODE_128 -> BarcodeFormat.CODE_128
            com.appsease.qrbarcode.domain.models.BarcodeFormat.ITF -> BarcodeFormat.ITF
            com.appsease.qrbarcode.domain.models.BarcodeFormat.CODABAR -> BarcodeFormat.CODABAR
            com.appsease.qrbarcode.domain.models.BarcodeFormat.UNKNOWN -> BarcodeFormat.QR_CODE
        }
    }
}
