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

package com.appsease.qrbarcode.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.appsease.qrbarcode.presentation.generator.TemplateSelectionScreen
import com.appsease.qrbarcode.presentation.history.HistoryScreen
import com.appsease.qrbarcode.presentation.scanner.ScannerScreen
import com.appsease.qrbarcode.presentation.settings.SettingsScreen
import timber.log.Timber
import org.koin.compose.koinInject
import com.appsease.qrbarcode.presentation.scanner.ScannerViewModel

/**
 * Bottom navigation graph - Nested NavHost for Scanner/Generator/History/Settings
 * This is contained within MainScreen and handles switching between bottom nav tabs
 *
 * @param navController Nested NavController for bottom nav (local to MainScreen)
 * @param parentNavController Parent NavController for navigating outside bottom nav
 */
@Composable
fun BottomNavHost(
    navController: NavHostController,
    parentNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Scanner,
        modifier = modifier
    ) {
        // ============ SCANNER SCREEN ============
        composable<Screen.Scanner> {
            Timber.tag("QC BottomNavHost").d("composable - Composing Scanner screen")

            // Get the scanner ViewModel to access scan result
            val scannerViewModel: ScannerViewModel = koinInject()

            ScannerScreen(
                viewModel = scannerViewModel,
                onNavigateToDetail = {
                    Timber.tag("QC BottomNavHost").d("onNavigateToDetail - Callback triggered")

                    // Get the scan result from the ViewModel state
                    val currentState = scannerViewModel.state.value
                    if (currentState.scanningState is com.appsease.qrbarcode.presentation.scanner.state.ScanningState.Success) {
                        val result = (currentState.scanningState as com.appsease.qrbarcode.presentation.scanner.state.ScanningState.Success).result
                        Timber.tag("QC BottomNavHost").d("onNavigateToDetail - Navigating with data: ${result.displayValue}")

                        parentNavController.navigate(
                            Screen.ScanDetail(
                                rawValue = result.rawValue,
                                displayValue = result.displayValue,
                                format = result.format.name,
                                contentType = result.contentType.name,
                                timestamp = result.timestamp
                            )
                        )
                        Timber.tag("QC BottomNavHost").d("onNavigateToDetail - Navigation executed")
                    } else {
                        Timber.tag("QC BottomNavHost").e("onNavigateToDetail - ERROR: State is not Success!")
                    }
                }
            )
        }

        // ============ GENERATOR SCREEN ============
        composable<Screen.Generator> {
            TemplateSelectionScreen(
                onTemplateSelected = { templateId ->
                    Timber.tag("TemplateSelection").d("Selected template: $templateId")
                    parentNavController.navigate(Screen.Creation(templateId = templateId))
                }
            )
        }

        // ============ HISTORY SCREEN ============
        composable<Screen.History> {
            HistoryScreen(
                navController = parentNavController  // Use parent for navigating to Detail
            )
        }

        // ============ SETTINGS SCREEN ============
        composable<Screen.Settings> {
            SettingsScreen(
                navController = parentNavController  // Use parent for settings detail screens
            )
        }
    }
}
