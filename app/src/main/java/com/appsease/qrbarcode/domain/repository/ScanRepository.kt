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

package com.appsease.qrbarcode.domain.repository

import com.appsease.qrbarcode.domain.models.ScanHistory
import kotlinx.coroutines.flow.Flow

interface ScanRepository {
    fun getAllHistory(): Flow<List<ScanHistory>>
    fun getFavorites(): Flow<List<ScanHistory>>
    fun searchHistory(query: String): Flow<List<ScanHistory>>
    fun getHistoryByType(type: String): Flow<List<ScanHistory>>
    suspend fun getHistoryById(id: Long): ScanHistory?
    suspend fun insertScan(history: ScanHistory): Long
    suspend fun updateScan(history: ScanHistory)
    suspend fun deleteScan(history: ScanHistory)
    suspend fun deleteByIds(ids: List<Long>)
    suspend fun deleteOlderThan(timestamp: Long)
    suspend fun deleteAll()
    suspend fun getCount(): Int
    suspend fun findDuplicate(format: String, content: String): ScanHistory?
}
