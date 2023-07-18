package de.dom.cishome.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import de.dom.cishome.myapplication.compose.home.home
import de.dom.cishome.myapplication.compose.membership.page.MembershipWelcomePage
import de.dom.cishome.myapplication.compose.player.pages.NewPlayerPage
import de.dom.cishome.myapplication.compose.player.pages.PlayerDetailPage
import de.dom.cishome.myapplication.compose.player.pages.PlayerInfoPage
import de.dom.cishome.myapplication.compose.player.pages.PlayerWelcomePage
import de.dom.cishome.myapplication.compose.player.service.PlayerService
import de.dom.cishome.myapplication.compose.playground.pages.PlayGroundPage
import de.dom.cishome.myapplication.compose.shared.FileRep
import de.dom.cishome.myapplication.compose.tunier.page.TurnierWelcomePage

@ExperimentalUnitApi
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContent {
             MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(  )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalUnitApi
@ExperimentalPermissionsApi
fun App(){

    var state = rememberPermissionState(
        android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
    )
    val repo = FileRep( LocalContext.current , state );
    val playerService: PlayerService = PlayerService( repo );

    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = "home") {
        composable("home"){
            home(navController)
        }

        composable("membership"){
            MembershipWelcomePage()
        }
        composable("turnier"){
            TurnierWelcomePage( navController )
        }
        composable("platz"){
            PlayGroundPage( navController )
        }
        
        playerGraph( navController = navController , playerService = playerService)

    }

    /*
    TopAppBar(
        title = { Text("Team Manager") },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
            }
        }
    )
     */


}

@Composable
fun Greeting(name: String, nav: NavController) {
    Text(
        text = "Hello $name!"
    )

    Button(onClick = { nav.navigate("test") }) {
        Text(text = "Click Me")
    }
}


@Composable
fun MyNavBar(){
    var selectedItem = 0;
    val items = listOf("Songs", "Artists", "Playlists")

    Button(onClick = {
        }) {

    }

    return NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

