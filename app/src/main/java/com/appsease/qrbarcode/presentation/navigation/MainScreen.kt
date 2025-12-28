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

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.appsease.qrbarcode.presentation.components.BottomNavigationBar

/**
 * Main screen - Container for the bottom navigation experience
 * This screen has its own Scaffold with bottom bar
 * It contains the BottomNavHost (nested navigation for Scanner/Generator/History/Settings)
 *
 * @param parentNavController Parent NavController for navigating outside bottom nav
 * @param initialTab The initial tab to navigate to (0=Scanner, 1=Generator, 2=History, 3=Settings), null=remember last
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    parentNavController: NavHostController,
    initialTab: Int? = null
) {
    // Local nav controller for bottom nav (nested navigation)
    val bottomNavController = rememberNavController()
    val context = LocalContext.current
    var showExitDialog by remember { mutableStateOf(false) }

    // Navigate to initial tab if specified
    LaunchedEffect(initialTab) {
        if (initialTab != null) {
            val route = when (initialTab) {
                0 -> Screen.Scanner
                1 -> Screen.Generator
                2 -> Screen.History
                3 -> Screen.Settings
                else -> null
            }
            if (route != null) {
                bottomNavController.navigate(route) {
                    popUpTo(bottomNavController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

    val currentBackStackEntry by bottomNavController.currentBackStackEntryAsState()

    // Handle back press - check if we're at root of bottom nav
    BackHandler {
        val canGoBack = bottomNavController.previousBackStackEntry != null
        if (canGoBack) {
            bottomNavController.popBackStack()
        } else {
            // At root level - show exit confirmation
            showExitDialog = true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = bottomNavController
            )
        }
    ) { innerPadding ->
        BottomNavHost(
            navController = bottomNavController,
            parentNavController = parentNavController,
            modifier = Modifier.padding(innerPadding)
        )
    }

    // Exit confirmation bottom sheet
    if (showExitDialog) {
        ModalBottomSheet(
            onDismissRequest = { showExitDialog = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            ExitConfirmationContent(
                onConfirm = {
                    showExitDialog = false
                    (context as? androidx.activity.ComponentActivity)?.finish()
                },
                onCancel = {
                    showExitDialog = false
                }
            )
        }
    }
}

@Composable
private fun ExitConfirmationContent(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Exit QR & Barcode Scanner?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Are you sure you want to exit the app?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onConfirm,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Exit")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}
