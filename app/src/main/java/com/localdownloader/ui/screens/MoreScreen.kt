package com.localdownloader.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.rounded.CloudDownload
import androidx.compose.material.icons.rounded.HelpOutline
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material.icons.rounded.SystemUpdate
import androidx.compose.material.icons.rounded.Transform
import androidx.compose.material.icons.rounded.Web
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.localdownloader.R
import com.localdownloader.ui.components.PreferenceNavigationRow
import com.localdownloader.ui.components.PreferencePageScaffold
import com.localdownloader.ui.components.PreferenceSubtitle

@Composable
fun MoreScreen(
    onOpenQueue: () -> Unit,
    onOpenHistory: () -> Unit,
    onOpenCompress: () -> Unit,
    onOpenConvert: () -> Unit,
    onOpenVideo: () -> Unit,
    onOpenMusic: () -> Unit,
    onOpenYoutubeAccess: () -> Unit,
    onOpenCookies: () -> Unit,
    onOpenUpdates: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenHelp: () -> Unit,
    onOpenVault: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PreferencePageScaffold(
        title = stringResource(R.string.more_title),
        onBack = null,
        modifier = modifier,
    ) {
        item {
            PreferenceSubtitle(text = stringResource(R.string.hc_downloads_storage))
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.CloudDownload,
                title = stringResource(R.string.more_queue_title),
                subtitle = stringResource(R.string.hc_active_paused_and_queued_tasks),
                onClick = onOpenQueue,
            )
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.History,
                title = stringResource(R.string.more_history_title),
                subtitle = stringResource(R.string.hc_completed_download_history),
                onClick = onOpenHistory,
            )
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Filled.Lock,
                title = stringResource(R.string.more_vault_title),
                subtitle = stringResource(R.string.hc_pin_protected_local_storage),
                onClick = onOpenVault,
            )
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.Settings,
                title = stringResource(R.string.common_settings),
                subtitle = stringResource(R.string.hc_preferences_and_configuration),
                onClick = onOpenSettings,
            )
        }
        item {
            PreferenceSubtitle(text = stringResource(R.string.hc_network_tools))
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.Web,
                title = stringResource(R.string.more_cookies_title),
                subtitle = stringResource(R.string.hc_saved_website_sessions),
                onClick = onOpenCookies,
            )
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.Shield,
                title = stringResource(R.string.more_youtube_access_title),
                subtitle = stringResource(R.string.hc_youtube_login_and_session_hints),
                onClick = onOpenYoutubeAccess,
            )
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.SystemUpdate,
                title = stringResource(R.string.more_updates_title),
                subtitle = stringResource(R.string.hc_app_yt_dlp_and_ffmpeg_updates),
                onClick = onOpenUpdates,
            )
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.HelpOutline,
                title = stringResource(R.string.common_help),
                subtitle = stringResource(R.string.hc_guides_and_troubleshooting),
                onClick = onOpenHelp,
            )
        }
        item {
            PreferenceSubtitle(text = stringResource(R.string.hc_media_playback_conversion))
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Outlined.PlayCircle,
                title = stringResource(R.string.more_video_player_title),
                subtitle = stringResource(R.string.hc_built_in_video_player),
                onClick = onOpenVideo,
            )
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.MusicNote,
                title = stringResource(R.string.more_music_player_title),
                subtitle = stringResource(R.string.hc_built_in_audio_player),
                onClick = onOpenMusic,
            )
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.SwapHoriz,
                title = stringResource(R.string.more_converter_title),
                subtitle = stringResource(R.string.hc_convert_media_formats),
                onClick = onOpenConvert,
            )
        }
        item {
            PreferenceNavigationRow(
                icon = Icons.Rounded.Transform,
                title = stringResource(R.string.more_compressor_title),
                subtitle = stringResource(R.string.hc_compress_video_files),
                onClick = onOpenCompress,
            )
        }
    }
}

