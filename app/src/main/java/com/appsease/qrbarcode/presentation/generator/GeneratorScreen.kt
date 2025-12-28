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

package com.appsease.qrbarcode.presentation.generator

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.appsease.qrbarcode.presentation.generator.components.BarcodeFormatSelector
import com.appsease.qrbarcode.presentation.generator.components.CodePreview
import com.appsease.qrbarcode.presentation.generator.components.ContentTypeDropdown
import com.appsease.qrbarcode.presentation.generator.components.InputForm
import com.appsease.qrbarcode.presentation.generator.state.GeneratorEvent
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun GeneratorScreen(
    viewModel: GeneratorViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        // Real-time preview at the top with fixed size
        CodePreview(
            bitmap = state.generatedCode?.bitmap,
            isGenerating = state.isGenerating,
            error = state.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content type dropdown
        ContentTypeDropdown(
            selectedType = state.selectedContentType,
            onTypeSelected = { type ->
                viewModel.onEvent(GeneratorEvent.OnContentTypeSelected(type))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Barcode format selector (1D/2D tabs)
        BarcodeFormatSelector(
            selectedType = state.selectedBarcodeType,
            selectedFormat = state.selectedBarcodeFormat,
            onTypeSelected = { type ->
                viewModel.onEvent(GeneratorEvent.OnBarcodeTypeSelected(type))
            },
            onFormatSelected = { format ->
                viewModel.onEvent(GeneratorEvent.OnBarcodeFormatSelected(format))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input form
        InputForm(
            contentType = state.selectedContentType,
            content = state.inputContent,
            onContentChange = { content ->
                viewModel.onEvent(GeneratorEvent.OnInputChanged(content))
            }
        )

        // Action buttons (only show when code is generated)
        if (state.generatedCode != null) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        state.generatedCode?.bitmap?.let { bitmap ->
                            shareImage(context, bitmap)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Share")
                }

                FilledTonalButton(
                    onClick = {
                        viewModel.onEvent(GeneratorEvent.OnSaveClicked)
                        Toast.makeText(
                            context,
                            "Saved to history",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private fun shareImage(context: Context, bitmap: Bitmap) {
    try {
        // Save bitmap to cache directory
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "qr_code_${System.currentTimeMillis()}.png")

        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        val contentUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share QR Code"))
    } catch (e: Exception) {
        Toast.makeText(context, "Failed to share image", Toast.LENGTH_SHORT).show()
    }
}
