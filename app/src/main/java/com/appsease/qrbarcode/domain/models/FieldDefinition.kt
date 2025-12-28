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

/**
 * Defines a single field in a template's form
 */
data class FieldDefinition(
    val key: String,                          // "email", "phone", etc.
    val label: String,                        // "Email Address"
    val type: FieldType,
    val required: Boolean = false,
    val placeholder: String? = null,
    val helperText: String? = null,
    val validation: ValidationRule? = null,
    val defaultValue: String? = null,
    val options: List<String>? = null         // For dropdown fields
)
