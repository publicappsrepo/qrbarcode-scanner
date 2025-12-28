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
 * Types of input fields for dynamic forms
 */
enum class FieldType {
    TEXT,              // Single-line text
    MULTILINE_TEXT,    // Multi-line text area
    EMAIL,             // Email with validation
    PHONE,             // Phone with formatting
    URL,               // URL with https:// prefix helper
    NUMBER,            // Numeric input
    DROPDOWN,          // Dropdown selection
    DATE,              // Date picker
    TIME,              // Time picker
    DATETIME,          // Date & time picker
    CHECKBOX           // Boolean checkbox
}
