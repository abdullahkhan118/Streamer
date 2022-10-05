package com.horux.streamer.video

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.horux.streamer.AGORA_APP_ID
import io.agora.agorauikit_android.AgoraConnectionData
import io.agora.agorauikit_android.AgoraSettings
import io.agora.agorauikit_android.AgoraVideoViewer

@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoScreen(
    roomName: String,
    onNavigateUp: () -> Unit = {},
    viewModel: VideoViewModel = VideoViewModel()
) {

    var agoraView: AgoraVideoViewer? = null

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            viewModel.onPermissionsResult(
                it[Manifest.permission.RECORD_AUDIO] == true,
                it[Manifest.permission.CAMERA] == true,
            )
        }
    )

    LaunchedEffect(key1 = true) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
            ),
        )
    }

    BackHandler() {
        agoraView?.leaveChannel()
        onNavigateUp()
    }

    if (viewModel.hasAudioPermission.value && viewModel.hasCameraPermission.value) {
        AndroidView(factory = {
            AgoraVideoViewer(
                it,
                connectionData = AgoraConnectionData(
                    appId = AGORA_APP_ID
                ),
                AgoraVideoViewer.Style.FLOATING,
                AgoraSettings(),
                null
            ).also {
            it.join(roomName)
            agoraView = it
        }
        }, modifier = Modifier.fillMaxSize())
    }
//    else Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Audio & Camera Permissions are required", color = MaterialTheme.colors.onError)
//        Button(onClick = {
//            permissionLauncher.launch(
//                arrayOf(
//                    Manifest.permission.RECORD_AUDIO,
//                    Manifest.permission.CAMERA
//                ),
//            )
//        }) {
//            Text(text = "Show Permission Request")
//        }
//    }

}