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
 * Validation rules for form fields
 */
sealed class ValidationRule {
    object Email : ValidationRule()
    object Phone : ValidationRule()
    object Url : ValidationRule()
    data class MinLength(val min: Int) : ValidationRule()
    data class MaxLength(val max: Int) : ValidationRule()
    data class Pattern(val regex: Regex, val message: String) : ValidationRule()
    data class Required(val message: String = "This field is required") : ValidationRule()
}
