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

package com.appsease.qrbarcode.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "generated_codes",
    indices = [
        Index(value = ["created_at"], name = "idx_generated_codes_created_at"),
        Index(value = ["template_id"], name = "idx_generated_codes_template_id"),
        Index(value = ["is_favorite"], name = "idx_generated_codes_is_favorite")
    ]
)
data class GeneratedCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // Template & Format
    @ColumnInfo(name = "template_id")
    val templateId: String,

    @ColumnInfo(name = "template_name")
    val templateName: String,

    @ColumnInfo(name = "barcode_format")
    val barcodeFormat: String,

    @ColumnInfo(name = "barcode_type")
    val barcodeType: String,

    // User Content
    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "note")
    val note: String? = null,

    @ColumnInfo(name = "content_fields")
    val contentFields: String, // JSON

    @ColumnInfo(name = "formatted_content")
    val formattedContent: String,

    // Customization
    @ColumnInfo(name = "foreground_color")
    val foregroundColor: Int,

    @ColumnInfo(name = "background_color")
    val backgroundColor: Int,

    @ColumnInfo(name = "size")
    val size: Int,

    @ColumnInfo(name = "error_correction")
    val errorCorrection: String? = null,

    @ColumnInfo(name = "margin")
    val margin: Int,

    // Timestamps
    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,

    // Metadata
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "scan_count")
    val scanCount: Int = 0
)
