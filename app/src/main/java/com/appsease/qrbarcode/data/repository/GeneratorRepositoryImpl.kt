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

import com.appsease.qrbarcode.data.local.database.dao.GeneratedCodeDao
import com.appsease.qrbarcode.data.mappers.toDomain
import com.appsease.qrbarcode.data.mappers.toDomainList
import com.appsease.qrbarcode.data.mappers.toEntity
import com.appsease.qrbarcode.domain.models.GeneratedCodeData
import com.appsease.qrbarcode.domain.repository.GeneratorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GeneratorRepositoryImpl(
    private val generatedCodeDao: GeneratedCodeDao
) : GeneratorRepository {

    override fun getAllGenerated(): Flow<List<GeneratedCodeData>> {
        return generatedCodeDao.getAllGenerated().map { it.toDomainList() }
    }

    override fun getFavorites(): Flow<List<GeneratedCodeData>> {
        return generatedCodeDao.getFavorites().map { it.toDomainList() }
    }

    override fun searchGenerated(query: String): Flow<List<GeneratedCodeData>> {
        return generatedCodeDao.searchGenerated(query).map { it.toDomainList() }
    }

    override fun getGeneratedByType(type: String): Flow<List<GeneratedCodeData>> {
        return generatedCodeDao.getGeneratedByType(type).map { it.toDomainList() }
    }

    override suspend fun getGeneratedById(id: Long): GeneratedCodeData? {
        return generatedCodeDao.getGeneratedById(id)?.toDomain()
    }

    override suspend fun insertGenerated(code: GeneratedCodeData): Long {
        return generatedCodeDao.insert(code.toEntity())
    }

    override suspend fun updateGenerated(code: GeneratedCodeData) {
        generatedCodeDao.update(code.toEntity())
    }

    override suspend fun deleteGenerated(code: GeneratedCodeData) {
        generatedCodeDao.delete(code.toEntity())
    }

    override suspend fun deleteByIds(ids: List<Long>) {
        generatedCodeDao.deleteByIds(ids)
    }

    override suspend fun deleteAll() {
        generatedCodeDao.deleteAll()
    }

    override suspend fun getCount(): Int {
        return generatedCodeDao.getCount()
    }
}
