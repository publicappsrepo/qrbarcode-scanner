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

enum class ContentType(val displayName: String) {
    URL("Website"),
    EMAIL("Email"),
    PHONE("Phone"),
    SMS("SMS"),
    WIFI("WiFi"),
    CONTACT("Contact"),
    CALENDAR("Calendar Event"),
    GEO("Location"),
    TEXT("Plain Text"),
    CRYPTO("Cryptocurrency"),
    PRODUCT("Product");

    companion object {
        fun detectFromContent(content: String): ContentType {
            return when {
                content.startsWith("http://", ignoreCase = true) ||
                        content.startsWith("https://", ignoreCase = true) -> URL

                content.startsWith("mailto:", ignoreCase = true) ||
                        android.util.Patterns.EMAIL_ADDRESS.matcher(content).matches() -> EMAIL

                content.startsWith("tel:", ignoreCase = true) ||
                        content.matches(Regex("^[+]?[0-9]{10,}$")) -> PHONE

                content.startsWith("smsto:", ignoreCase = true) ||
                        content.startsWith("sms:", ignoreCase = true) -> SMS

                content.startsWith("WIFI:", ignoreCase = true) -> WIFI

                content.startsWith("BEGIN:VCARD", ignoreCase = true) -> CONTACT

                content.startsWith("BEGIN:VEVENT", ignoreCase = true) -> CALENDAR

                content.startsWith("geo:", ignoreCase = true) ||
                        content.matches(Regex("^-?\\d+\\.\\d+,-?\\d+\\.\\d+$")) -> GEO

                content.startsWith("bitcoin:", ignoreCase = true) ||
                        content.startsWith("ethereum:", ignoreCase = true) ||
                        content.matches(Regex("^(bc1|0x)[a-zA-Z0-9]{25,}$")) -> CRYPTO

                content.matches(Regex("^[0-9]{8,14}$")) -> PRODUCT

                else -> TEXT
            }
        }

        fun fromString(typeString: String): ContentType {
            return try {
                valueOf(typeString)
            } catch (e: IllegalArgumentException) {
                TEXT
            }
        }
    }
}
