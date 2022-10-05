package com.horux.streamer.room

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

@androidx.compose.runtime.Composable
fun RoomScreen(
    onNavigate: (String) -> Unit,
    viewModel: RoomViewModel = RoomViewModel()
) {
    
    LaunchedEffect(key1 = true){
        viewModel.onJoinEvent.collectLatest { name ->
            onNavigate("video_screen/$name")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.roomName.value.text,
            onValueChange = viewModel::onRoomEnter,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text("Enter room name here!")
        })
        viewModel.roomName.value.error?.let{ error ->
            Text(text = error, color = MaterialTheme.colors.onError)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = viewModel::onJoinRoom) {
            Text(text = "Join Room")
        }
    }
    
}