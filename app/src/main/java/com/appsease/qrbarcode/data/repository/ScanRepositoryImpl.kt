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

package com.appsease.qrbarcode.data.repository

import com.appsease.qrbarcode.data.local.database.dao.ScanHistoryDao
import com.appsease.qrbarcode.data.mappers.toDomain
import com.appsease.qrbarcode.data.mappers.toDomainList
import com.appsease.qrbarcode.data.mappers.toEntity
import com.appsease.qrbarcode.domain.models.ScanHistory
import com.appsease.qrbarcode.domain.repository.ScanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class ScanRepositoryImpl(
    private val scanHistoryDao: ScanHistoryDao
) : ScanRepository {

    override fun getAllHistory(): Flow<List<ScanHistory>> {
        return scanHistoryDao.getAllHistory().map { it.toDomainList() }
    }

    override fun getFavorites(): Flow<List<ScanHistory>> {
        return scanHistoryDao.getFavorites().map { it.toDomainList() }
    }

    override fun searchHistory(query: String): Flow<List<ScanHistory>> {
        return scanHistoryDao.searchHistory(query).map { it.toDomainList() }
    }

    override fun getHistoryByType(type: String): Flow<List<ScanHistory>> {
        return scanHistoryDao.getHistoryByType(type).map { it.toDomainList() }
    }

    override suspend fun getHistoryById(id: Long): ScanHistory? {
        return scanHistoryDao.getHistoryById(id)?.toDomain()
    }

    override suspend fun insertScan(history: ScanHistory): Long {
        Timber.tag("QC ScanRepositoryImpl").d("insertScan - Called with content: ${history.content}")
        return try {
            val id = scanHistoryDao.insert(history.toEntity())
            Timber.tag("QC ScanRepositoryImpl").d("insertScan - Insert successful, ID: $id")
            id
        } catch (e: Exception) {
            Timber.tag("QC ScanRepositoryImpl").e(e, "insertScan - Insert failed")
            throw e
        }
    }

    override suspend fun updateScan(history: ScanHistory) {
        scanHistoryDao.update(history.toEntity())
    }

    override suspend fun deleteScan(history: ScanHistory) {
        scanHistoryDao.delete(history.toEntity())
    }

    override suspend fun deleteByIds(ids: List<Long>) {
        scanHistoryDao.deleteByIds(ids)
    }

    override suspend fun deleteOlderThan(timestamp: Long) {
        scanHistoryDao.deleteOlderThan(timestamp)
    }

    override suspend fun deleteAll() {
        scanHistoryDao.deleteAll()
    }

    override suspend fun getCount(): Int {
        return scanHistoryDao.getCount()
    }

    override suspend fun findDuplicate(format: String, content: String): ScanHistory? {
        return scanHistoryDao.findDuplicate(format, content)?.toDomain()
    }
}
