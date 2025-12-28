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

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import com.appsease.qrbarcode.domain.models.ScanResult
import timber.log.Timber
import java.util.concurrent.Executors

@Composable
fun CameraPreview(
    onBarcodeDetected: (ScanResult) -> Unit,
    isFlashlightOn: Boolean = false,
    isPreviewActive: Boolean = true,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    // Only show camera preview when active
    if (isPreviewActive) {
        AndroidView(
            factory = { previewView },
            modifier = modifier.fillMaxSize(),
            update = {
                startCamera(
                    context = context,
                    previewView = previewView,
                    lifecycleOwner = lifecycleOwner,
                    cameraExecutor = cameraExecutor,
                    onBarcodeDetected = onBarcodeDetected,
                    isFlashlightOn = isFlashlightOn
                )
            }
        )
    } else {
        // Stop camera when preview is inactive
        DisposableEffect(Unit) {
            stopCamera(context)
            onDispose { }
        }
    }
}

private fun startCamera(
    context: Context,
    previewView: PreviewView,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    cameraExecutor: java.util.concurrent.ExecutorService,
    onBarcodeDetected: (ScanResult) -> Unit,
    isFlashlightOn: Boolean = false
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        try {
            val cameraProvider = cameraProviderFuture.get()

            // Preview use case
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            // Image analysis use case for barcode scanning
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer(onBarcodeDetected))
                }

            // Select back camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // Unbind all use cases before rebinding
            cameraProvider.unbindAll()

            // Bind use cases to camera
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer
            )

            // Control flashlight
            camera.cameraControl.enableTorch(isFlashlightOn)

            Timber.d("Camera started successfully, flashlight: $isFlashlightOn")
        } catch (e: Exception) {
            Timber.e(e, "Failed to start camera")
        }
    }, ContextCompat.getMainExecutor(context))
}

private fun stopCamera(context: Context) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        try {
            val cameraProvider = cameraProviderFuture.get()
            // Unbind all use cases to release camera resources
            cameraProvider.unbindAll()
            Timber.d("Camera stopped and resources released")
        } catch (e: Exception) {
            Timber.e(e, "Failed to stop camera")
        }
    }, ContextCompat.getMainExecutor(context))
}
