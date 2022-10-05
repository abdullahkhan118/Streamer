package com.horux.streamer.video

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VideoViewModel: ViewModel() {

    private val _hasAudioPermission = mutableStateOf<Boolean>(false)
    val hasAudioPermission: State<Boolean> = _hasAudioPermission

    private val _hasCameraPermission = mutableStateOf<Boolean>(false)
    val hasCameraPermission: State<Boolean> = _hasCameraPermission

    fun onPermissionsResult(
        acceptedAudioPermission: Boolean,
        acceptedCameraPermission: Boolean,
    ){
        _hasAudioPermission.value = acceptedAudioPermission
        _hasCameraPermission.value = acceptedCameraPermission
    }

}