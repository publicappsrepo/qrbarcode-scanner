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

import androidx.room.Room
import com.appsease.qrbarcode.data.local.database.QRBarcodeDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    // Room Database
    single {
        timber.log.Timber.tag("QC DatabaseModule").d("init - Creating Room database")
        val db = Room.databaseBuilder(
            androidContext(),
            QRBarcodeDatabase::class.java,
            "qrbarcode_database"
        )
            .addMigrations(com.appsease.qrbarcode.data.local.database.MIGRATION_1_2)
            .fallbackToDestructiveMigration(false)
            .build()
        timber.log.Timber.tag("QC DatabaseModule").d("init - Room database created successfully")
        db
    }

    // DAOs
    single {
        timber.log.Timber.tag("QC DatabaseModule").d("init - Creating ScanHistoryDao")
        get<QRBarcodeDatabase>().scanHistoryDao()
    }
    single {
        timber.log.Timber.tag("QC DatabaseModule").d("init - Creating GeneratedCodeDao")
        get<QRBarcodeDatabase>().generatedCodeDao()
    }
}
