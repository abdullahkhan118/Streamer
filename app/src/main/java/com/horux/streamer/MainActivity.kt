package com.horux.streamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.horux.streamer.room.RoomScreen
import com.horux.streamer.ui.theme.StreamerTheme
import com.horux.streamer.video.VideoScreen

const val AGORA_APP_ID = "efb53be136ab4336b2f718b2deca173a"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StreamerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "room_screen"){
                        composable(route = "room_screen")
                        {
                            RoomScreen(onNavigate = navController::navigate)
                        }
                        composable("video_screen/{roomName}", arguments = listOf(
                            navArgument(name = "roomName"){
                                type = NavType.StringType
                            }
                        )){
                            val roomName = it.arguments?.getString("roomName") ?: return@composable
                            VideoScreen(roomName = roomName, onNavigateUp = navController::navigateUp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StreamerTheme {
        Greeting("Android")
    }
}