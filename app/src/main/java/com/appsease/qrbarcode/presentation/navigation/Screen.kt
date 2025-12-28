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

package com.appsease.qrbarcode.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    // Main container with bottom nav
    @Serializable
    data class Main(
        val initialTab: Int? = null
    ) : Screen()

    // Bottom nav destinations (nested in Main)
    @Serializable
    data object Scanner : Screen()

    @Serializable
    data object Generator : Screen()

    @Serializable
    data object History : Screen()

    @Serializable
    data object Settings : Screen()

    // App-level screens (outside bottom nav)
    // Generator flow screens
    @Serializable
    data class Creation(
        val templateId: String,
        val codeId: Long? = null // For edit mode
    ) : Screen()

    @Serializable
    data class CodeDetails(
        val codeId: Long
    ) : Screen()

    @Serializable
    data class ScanDetail(
        val rawValue: String,
        val displayValue: String,
        val format: String, // BarcodeFormat name
        val contentType: String, // ContentType name
        val timestamp: Long
    ) : Screen()

    @Serializable
    data class ScanHistoryDetail(
        val scanId: Long
    ) : Screen()
}
