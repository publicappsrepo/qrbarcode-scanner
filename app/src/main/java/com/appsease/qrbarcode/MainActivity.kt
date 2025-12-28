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

package com.appsease.qrbarcode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.appsease.qrbarcode.data.preferences.PreferencesManager
import com.appsease.qrbarcode.presentation.onboarding.OnboardingScreen
import com.appsease.qrbarcode.theme.QRBarcodeTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val preferencesManager: PreferencesManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            QRBarcodeTheme {
                MainScreen(preferencesManager = preferencesManager)
            }
        }
    }
}

@Composable
fun MainScreen(preferencesManager: PreferencesManager) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val isOnboardingCompleted by preferencesManager.isOnboardingCompleted.collectAsState(initial = null)

    // Show nothing until we know the actual value from DataStore
    when (isOnboardingCompleted) {
        null -> {
            // Loading state - show blank screen to prevent flash
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
        }
        false -> {
            // Show onboarding
            OnboardingScreen(
                onFinish = {
                    scope.launch {
                        preferencesManager.setOnboardingCompleted()
                    }
                }
            )
        }
        true -> {
            // Show main app with new navigation structure
            com.appsease.qrbarcode.presentation.navigation.QRBarcodeNavHost(
                navController = navController
            )
        }
    }
}