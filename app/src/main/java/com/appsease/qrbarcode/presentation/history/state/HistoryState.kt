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

package com.appsease.qrbarcode.presentation.history.state

import com.appsease.qrbarcode.domain.models.GeneratedCodeData
import com.appsease.qrbarcode.domain.models.ScanHistory

data class HistoryState(
    val selectedTab: HistoryTab = HistoryTab.ALL,
    val scannedHistory: List<ScanHistory> = emptyList(),
    val generatedHistory: List<GeneratedCodeData> = emptyList(),
    val combinedHistory: List<HistoryItemData> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class HistoryTab {
    ALL,
    SCANNED,
    GENERATED
}

enum class HistoryItemType {
    SCANNED,
    GENERATED
}

sealed class HistoryItemData(
    open val id: Long,
    open val timestamp: Long,
    open val type: HistoryItemType
) {
    data class Scanned(
        override val id: Long,
        override val timestamp: Long,
        val data: ScanHistory
    ) : HistoryItemData(id, timestamp, HistoryItemType.SCANNED)

    data class Generated(
        override val id: Long,
        override val timestamp: Long,
        val data: GeneratedCodeData
    ) : HistoryItemData(id, timestamp, HistoryItemType.GENERATED)
}

sealed interface HistoryEvent {
    data class OnTabSelected(val tab: HistoryTab) : HistoryEvent
    data class OnSearchQueryChanged(val query: String) : HistoryEvent
    data class OnFilterSelected(val filter: String?) : HistoryEvent
    data class OnToggleFavorite(val id: Long, val isFavorite: Boolean) : HistoryEvent
}
