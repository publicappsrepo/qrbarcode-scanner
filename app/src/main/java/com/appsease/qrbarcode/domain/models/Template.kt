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

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Template for QR code generation
 * Defines what fields to show and how to format the content
 */
data class Template(
    val id: String,                                              // "business_card", "wifi", etc.
    val name: String,                                            // "Business Card"
    val description: String,                                     // "Professional digital business card"
    val icon: ImageVector,
    val category: TemplateCategory,
    val defaultFormat: BarcodeFormat,
    val allowedFormats: List<BarcodeFormat>,                    // Formats compatible with this template
    val fields: List<FieldDefinition>,
    val formatContentProvider: (Map<String, String>) -> String  // Lambda to format field values into QR content
)
