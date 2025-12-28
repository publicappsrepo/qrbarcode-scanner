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

package com.appsease.qrbarcode.di

import com.appsease.qrbarcode.data.repository.GeneratorRepositoryImpl
import com.appsease.qrbarcode.data.repository.ScanRepositoryImpl
import com.appsease.qrbarcode.data.repository.SettingsRepositoryImpl
import com.appsease.qrbarcode.domain.repository.GeneratorRepository
import com.appsease.qrbarcode.domain.repository.ScanRepository
import com.appsease.qrbarcode.domain.repository.SettingsRepository
import org.koin.dsl.module

val repositoryModule = module {
    // Repositories
    single<ScanRepository> { ScanRepositoryImpl(get()) }
    single<GeneratorRepository> { GeneratorRepositoryImpl(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}
