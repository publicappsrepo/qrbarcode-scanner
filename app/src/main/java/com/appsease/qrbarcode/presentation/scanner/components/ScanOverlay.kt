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

package com.appsease.qrbarcode.presentation.scanner.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ScanOverlay(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Calculate scan area (square in the center) - doubled size (from 0.7 to 1.4, max 0.7 of height)
        val scanSize = (canvasWidth * 0.75f).coerceAtMost(canvasHeight * 0.6f)
        val left = (canvasWidth - scanSize) / 2
        val top = (canvasHeight - scanSize) / 2
        // Draw darkened background
        drawRect(
            color = Color.Black.copy(alpha = 0.5f),
            size = Size(size.width, size.height)
        )

        // Cut out the scan area (transparent)
        drawRoundRect(
            color = Color.Transparent,
            topLeft = Offset(left, top),
            size = Size(scanSize, scanSize),
            cornerRadius = CornerRadius(16.dp.toPx()),
            blendMode = BlendMode.Clear
        )

        // Draw corner markers
        val cornerLength = 40.dp.toPx()
        val cornerStroke = 4.dp.toPx()
        val cornerColor = Color.White

        // Top-left corner
        drawPath(
            path = Path().apply {
                moveTo(left, top + cornerLength)
                lineTo(left, top)
                lineTo(left + cornerLength, top)
            },
            color = cornerColor,
            style = Stroke(width = cornerStroke)
        )

        // Top-right corner
        drawPath(
            path = Path().apply {
                moveTo(left + scanSize - cornerLength, top)
                lineTo(left + scanSize, top)
                lineTo(left + scanSize, top + cornerLength)
            },
            color = cornerColor,
            style = Stroke(width = cornerStroke)
        )

        // Bottom-left corner
        drawPath(
            path = Path().apply {
                moveTo(left, top + scanSize - cornerLength)
                lineTo(left, top + scanSize)
                lineTo(left + cornerLength, top + scanSize)
            },
            color = cornerColor,
            style = Stroke(width = cornerStroke)
        )

        // Bottom-right corner
        drawPath(
            path = Path().apply {
                moveTo(left + scanSize - cornerLength, top + scanSize)
                lineTo(left + scanSize, top + scanSize)
                lineTo(left + scanSize, top + scanSize - cornerLength)
            },
            color = cornerColor,
            style = Stroke(width = cornerStroke)
        )
    }
}
