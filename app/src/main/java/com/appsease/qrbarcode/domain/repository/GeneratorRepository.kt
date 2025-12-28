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

import com.appsease.qrbarcode.domain.models.GeneratedCodeData
import kotlinx.coroutines.flow.Flow

interface GeneratorRepository {
    fun getAllGenerated(): Flow<List<GeneratedCodeData>>
    fun getFavorites(): Flow<List<GeneratedCodeData>>
    fun searchGenerated(query: String): Flow<List<GeneratedCodeData>>
    fun getGeneratedByType(type: String): Flow<List<GeneratedCodeData>>
    suspend fun getGeneratedById(id: Long): GeneratedCodeData?
    suspend fun insertGenerated(code: GeneratedCodeData): Long
    suspend fun updateGenerated(code: GeneratedCodeData)
    suspend fun deleteGenerated(code: GeneratedCodeData)
    suspend fun deleteByIds(ids: List<Long>)
    suspend fun deleteAll()
    suspend fun getCount(): Int
}
