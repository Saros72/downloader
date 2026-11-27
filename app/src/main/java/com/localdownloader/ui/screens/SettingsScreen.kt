package com.localdownloader.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BatteryChargingFull
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material.icons.rounded.CloudDownload
import androidx.compose.material.icons.rounded.EnergySavingsLeaf
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Subtitles
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.localdownloader.BuildConfig
import com.localdownloader.R
import com.localdownloader.ui.components.InlineFeedbackCard
import com.localdownloader.ui.components.PreferencePageScaffold
import com.localdownloader.ui.components.PreferencesHintCard
import com.localdownloader.ui.components.SettingItem
import com.localdownloader.viewmodel.FormatMessageScope
import com.localdownloader.viewmodel.FormatUiState

@Composable
fun SettingsScreen(
    uiState: FormatUiState,
    savedItemsCount: Int,
    modifier: Modifier = Modifier,
    mediaInfoMessage: String? = null,
    mediaErrorMessage: String? = null,
    onDismissMediaLibraryMessage: () -> Unit = {},
    onOpenAppearance: () -> Unit,
    onOpenDownloads: () -> Unit,
    onOpenSubtitles: () -> Unit,
    onOpenBattery: () -> Unit,
    onOpenStorage: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenAccess: () -> Unit,
    onOpenAdvanced: () -> Unit = {},
    onOpenAbout: () -> Unit,
    onOpenAppLog: () -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val pm = remember(context) { context.getSystemService(Context.POWER_SERVICE) as? PowerManager }
    var showBatteryHint by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && pm != null) {
                !pm.isIgnoringBatteryOptimizations(context.packageName)
            } else {
                false
            }
        )
    }

    val batteryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && pm != null) {
            showBatteryHint = !pm.isIgnoringBatteryOptimizations(context.packageName)
        }
    }

    val settingsInfoMessage = uiState.infoMessageFor(FormatMessageScope.SETTINGS)
    val settingsErrorMessage = uiState.errorMessageFor(FormatMessageScope.SETTINGS)

    PreferencePageScaffold(
        title = stringResource(R.string.settings_title),
        onBack = onBack,
        modifier = modifier,
    ) {
        settingsMessages(
            settingsInfoMessage = settingsInfoMessage,
            settingsErrorMessage = settingsErrorMessage,
            mediaInfoMessage = mediaInfoMessage,
            mediaErrorMessage = mediaErrorMessage,
            onDismissMediaLibraryMessage = onDismissMediaLibraryMessage,
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            item {
                AnimatedVisibility(
                    visible = showBatteryHint,
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    PreferencesHintCard(
                        title = stringResource(R.string.hc_battery_optimization),
                        description = stringResource(R.string.hc_disable_battery_optimization_to_allow_un),
                        icon = Icons.Rounded.EnergySavingsLeaf,
                        onClick = {
                            runCatching {
                                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                                    data = Uri.parse("package:${context.packageName}")
                                }
                                batteryLauncher.launch(intent)
                            }.onFailure {
                                runCatching {
                                    val fallback = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
                                    batteryLauncher.launch(fallback)
                                }
                            }
                        },
                    )
                }
            }
        }

        item {
            SettingItem(
                title = stringResource(R.string.settings_download_defaults_title),
                description = stringResource(R.string.hc_format_templates_containers_and_queue_be),
                icon = Icons.Rounded.CloudDownload,
                onClick = onOpenDownloads,
            )
        }
        item {
            SettingItem(
                title = stringResource(R.string.settings_subtitles_title),
                description = stringResource(R.string.hc_subtitle_auto_download_embedding_styling),
                icon = Icons.Rounded.Subtitles,
                onClick = onOpenSubtitles,
            )
        }
        item {
            SettingItem(
                title = stringResource(R.string.settings_storage_title),
                description = stringResource(R.string.hc_storage_directories_subfolders_and_cache),
                icon = Icons.Rounded.Folder,
                onClick = onOpenStorage,
            )
        }
        item {
            SettingItem(
                title = stringResource(R.string.settings_access_title),
                description = stringResource(R.string.hc_network_rules_cookies_and_youtube_access),
                icon = Icons.Rounded.Security,
                onClick = onOpenAccess,
            )
        }
        item {
            SettingItem(
                title = stringResource(R.string.settings_appearance_title),
                description = stringResource(R.string.hc_theme_mode_accent_colors_and_app_languag),
                icon = Icons.Rounded.Palette,
                onClick = onOpenAppearance,
            )
        }
        item {
            SettingItem(
                title = stringResource(R.string.settings_battery_title),
                description = stringResource(R.string.hc_download_threads_eco_mode_and_power_limi),
                icon = Icons.Rounded.BatteryChargingFull,
                onClick = onOpenBattery,
            )
        }
        item {
            SettingItem(
                title = stringResource(R.string.settings_notifications_title),
                description = stringResource(R.string.hc_completion_error_and_status_alerts),
                icon = Icons.Rounded.NotificationsActive,
                onClick = onOpenNotifications,
            )
        }
        item {
            SettingItem(
                title = stringResource(R.string.hc_advanced_settings),
                description = stringResource(R.string.hc_timeouts_rate_limits_filename_rules_play),
                icon = Icons.Rounded.Tune,
                onClick = onOpenAdvanced,
            )
        }
        item {
            SettingItem(
                title = stringResource(R.string.settings_app_log_title),
                description = stringResource(R.string.hc_application_logs_export_and_diagnostics),
                icon = Icons.Rounded.BugReport,
                onClick = onOpenAppLog,
            )
        }
        item {
            SettingItem(
                title = stringResource(R.string.settings_about_title),
                description = "Version ${BuildConfig.VERSION_NAME}, updates, and info",
                icon = Icons.Rounded.Info,
                onClick = onOpenAbout,
            )
        }
    }
}

private fun LazyListScope.settingsMessages(
    settingsInfoMessage: String?,
    settingsErrorMessage: String?,
    mediaInfoMessage: String?,
    mediaErrorMessage: String?,
    onDismissMediaLibraryMessage: () -> Unit,
) {
    if (!settingsInfoMessage.isNullOrBlank()) {
        item {
            InlineFeedbackCard(
                label = stringResource(R.string.settings_feedback_label),
                message = settingsInfoMessage,
                isError = false,
            )
        }
    }
    if (!settingsErrorMessage.isNullOrBlank()) {
        item {
            InlineFeedbackCard(
                label = stringResource(R.string.settings_feedback_label),
                message = settingsErrorMessage,
                isError = true,
            )
        }
    }
    if (!mediaInfoMessage.isNullOrBlank()) {
        item {
            InlineFeedbackCard(
                label = stringResource(R.string.settings_library_label),
                message = mediaInfoMessage,
                isError = false,
                onDismiss = onDismissMediaLibraryMessage,
            )
        }
    }
    if (!mediaErrorMessage.isNullOrBlank()) {
        item {
            InlineFeedbackCard(
                label = stringResource(R.string.settings_library_label),
                message = mediaErrorMessage,
                isError = true,
                onDismiss = onDismissMediaLibraryMessage,
            )
        }
    }
}

private fun buildAccessSummary(uiState: FormatUiState, context: android.content.Context): String {
    val resources = context.resources
    val networkSummary = if (uiState.allowMeteredDownloads) {
        resources.getString(R.string.settings_access_summary_cellular)
    } else {
        resources.getString(R.string.settings_access_summary_wifi)
    }
    val cookieSummary = if (uiState.cookieProfiles.isEmpty()) {
        resources.getString(R.string.settings_access_summary_no_cookies)
    } else {
        resources.getQuantityString(
            R.plurals.settings_access_summary_cookie_count,
            uiState.cookieProfiles.size,
            uiState.cookieProfiles.size,
        )
    }
    val youtubeSummary = if (uiState.youtubeAuthConfig.isConfigured()) {
        resources.getString(R.string.settings_access_summary_youtube_configured)
    } else {
        resources.getString(R.string.settings_access_summary_youtube_missing)
    }
    return "$networkSummary $cookieSummary $youtubeSummary"
}

