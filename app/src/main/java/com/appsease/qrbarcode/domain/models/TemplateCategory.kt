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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Categories for organizing QR code templates
 */
enum class TemplateCategory(
    val displayName: String,
    val icon: ImageVector
) {
    GENERAL(
        displayName = "General & Personal",
        icon = Icons.Default.Description
    ),
    COMMUNICATION(
        displayName = "Communication",
        icon = Icons.AutoMirrored.Filled.Message
    ),
    SOCIAL_WEB(
        displayName = "Social & Web",
        icon = Icons.Default.Language
    ),
    LOCATION_EVENTS(
        displayName = "Location & Events",
        icon = Icons.Default.Place
    ),
    BUSINESS(
        displayName = "Business & Professional",
        icon = Icons.Default.Business
    ),
    PRODUCT(
        displayName = "Product & Inventory",
        icon = Icons.Default.Inventory
    ),
    DOCUMENTS(
        displayName = "Documents & Files",
        icon = Icons.Default.FileCopy
    ),
    TICKETS(
        displayName = "Tickets & Passes",
        icon = Icons.Default.ConfirmationNumber
    )
}
