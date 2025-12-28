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

import com.appsease.qrbarcode.data.local.database.entities.ScanHistoryEntity
import com.appsease.qrbarcode.domain.models.BarcodeFormat
import com.appsease.qrbarcode.domain.models.ContentType
import com.appsease.qrbarcode.domain.models.ScanHistory
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

fun ScanHistoryEntity.toDomain(): ScanHistory {
    return ScanHistory(
        id = id,
        content = content,
        rawValue = rawValue,
        format = BarcodeFormat.fromString(format),
        contentType = ContentType.fromString(contentType),
        timestamp = timestamp,
        isFavorite = isFavorite,
        metadata = metadata?.let {
            try {
                Json.decodeFromString<Map<String, String>>(it)
            } catch (e: Exception) {
                null
            }
        }
    )
}

fun ScanHistory.toEntity(): ScanHistoryEntity {
    return ScanHistoryEntity(
        id = id,
        content = content,
        rawValue = rawValue,
        format = format.name,
        contentType = contentType.name,
        timestamp = timestamp,
        isFavorite = isFavorite,
        metadata = metadata?.let { Json.encodeToString(it) }
    )
}

fun List<ScanHistoryEntity>.toDomainList(): List<ScanHistory> = map { it.toDomain() }
