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

import com.appsease.qrbarcode.data.local.preferences.ThemePreferences
import com.appsease.qrbarcode.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val themePreferences: ThemePreferences
) : SettingsRepository {

    override fun getTheme(): Flow<String> {
        return themePreferences.getTheme()
    }

    override suspend fun setTheme(theme: String) {
        themePreferences.setTheme(theme)
    }

    override fun isDynamicColorEnabled(): Flow<Boolean> {
        return themePreferences.isDynamicColorEnabled()
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        themePreferences.setDynamicColor(enabled)
    }
}
