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

package com.appsease.qrbarcode.data.mappers

import com.appsease.qrbarcode.data.local.database.entities.GeneratedCodeEntity
import com.appsease.qrbarcode.domain.models.BarcodeFormat
import com.appsease.qrbarcode.domain.models.ErrorCorrectionLevel
import com.appsease.qrbarcode.domain.models.GeneratedCodeData
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

fun GeneratedCodeEntity.toDomain(): GeneratedCodeData {
    return GeneratedCodeData(
        id = id,
        templateId = templateId,
        templateName = templateName,
        barcodeFormat = BarcodeFormat.fromString(barcodeFormat),
        barcodeType = barcodeType,
        title = title,
        note = note,
        contentFields = try {
            Json.decodeFromString<Map<String, String>>(contentFields)
        } catch (e: Exception) {
            emptyMap()
        },
        formattedContent = formattedContent,
        foregroundColor = foregroundColor,
        backgroundColor = backgroundColor,
        size = size,
        errorCorrection = errorCorrection?.let {
            try {
                ErrorCorrectionLevel.valueOf(it)
            } catch (e: Exception) {
                null
            }
        },
        margin = margin,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isFavorite = isFavorite,
        scanCount = scanCount
    )
}

fun GeneratedCodeData.toEntity(): GeneratedCodeEntity {
    return GeneratedCodeEntity(
        id = id,
        templateId = templateId,
        templateName = templateName,
        barcodeFormat = barcodeFormat.name,
        barcodeType = barcodeType,
        title = title,
        note = note,
        contentFields = Json.encodeToString(contentFields),
        formattedContent = formattedContent,
        foregroundColor = foregroundColor,
        backgroundColor = backgroundColor,
        size = size,
        errorCorrection = errorCorrection?.name,
        margin = margin,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isFavorite = isFavorite,
        scanCount = scanCount
    )
}

fun List<GeneratedCodeEntity>.toDomainList(): List<GeneratedCodeData> = map { it.toDomain() }
